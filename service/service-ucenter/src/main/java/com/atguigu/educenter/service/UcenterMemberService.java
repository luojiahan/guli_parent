package com.atguigu.educenter.service;

import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.LoginInfoVo;
import com.atguigu.educenter.entity.vo.LoginVo;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author atguigu
 * @since 2022-05-14
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    // 登录
    String login(LoginVo loginVo);

    // 注册
    void register(RegisterVo registerVo);

    // 根据token获取登录信息
    LoginInfoVo getLoginInfo(String memberId);

    // 查询数据库当前用用户是否曾经使用过微信登录
    UcenterMember getByOpenid(String openid);

    // 查询某一天的注册人数
    Integer countRegisterByDay(String day);
}
