package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.LoginInfoVo;
import com.atguigu.educenter.entity.vo.LoginVo;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.servicebase.exceptiohandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-05-14
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 登录
    @Override
    public String login(LoginVo loginVo) {
        // 获取登录手机号和密码
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        // 判断对象是否为空
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "登录失败");
        }

        // 判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember member = baseMapper.selectOne(wrapper);

        // 判断会员是否存在
        if (member == null) {
            throw new GuliException(20001, "会员不存在");
        }
        // 判断密码
        // 数据库中的密码是加密的
        // 加密方式MD5

        if (!MD5.encrypt(password).equals(member.getPassword())) {
            throw new GuliException(20001, "用户密码不正确");
        }
        // 判断用户是否禁用:是否禁用 1（true）已禁用，  0（false）未禁用
        if (member.getIsDisabled() == 1) {
            throw new GuliException(20001, "用户状态异常");
        }
        // 登录成功后，使用JWT生成token字符串
        String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());

        return jwtToken;
    }

    // 注册
    @Override
    public void register(RegisterVo registerVo) {
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();
        //校验参数
        if(StringUtils.isEmpty(nickname) || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code)) {
            throw new GuliException(20001, "用户名、手机号、验证码或密码数据参数为空");
        }
        //校验校验验证码
        //从redis获取发送的验证码
        String mobleCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(mobleCode)) {
            throw new GuliException(20001,"验证码已过期");
        }
        //查询数据库中是否存在相同的手机号码
        Integer count = baseMapper.selectCount(new QueryWrapper<UcenterMember>().eq("mobile", mobile));
        if(count.intValue() > 0) {
            throw new GuliException(20001,"手机号已经注册");
        }
        //添加注册信息到数据库
        UcenterMember member = new UcenterMember();
        member.setNickname(nickname);
        member.setMobile(registerVo.getMobile());
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(0);
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(member);
        // this.save(member);
    }

    // 根据token获取登录信息
    @Override
    public LoginInfoVo getLoginInfo(String memberId) {
        UcenterMember ucenterMember = baseMapper.selectById(memberId);
        LoginInfoVo loginInfoVo = new LoginInfoVo();
        BeanUtils.copyProperties(ucenterMember, loginInfoVo);
        return loginInfoVo;
    }

    // 查询数据库当前用用户是否曾经使用过微信登录
    @Override
    public UcenterMember getByOpenid(String openid) {
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openid);

        UcenterMember member = baseMapper.selectOne(queryWrapper);
        return member;
    }
}
