package com.atguigu.educenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.LoginInfoVo;
import com.atguigu.educenter.entity.vo.LoginVo;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.entity.vo.UcenterMemberVo;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.servicebase.exceptiohandler.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-05-14
 */
@Api(tags = "会员接口")
@RestController
//@CrossOrigin
@RequestMapping("/educenter/member")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    // 登录
    @ApiOperation(value = "会员登录")
    @PostMapping("login")
    public R loginUser(
            @ApiParam(name = "loginVo", value = "会员登录信息对象", required = true)
            @RequestBody LoginVo loginVo) {
        // 调用service方法实现登录
        // 返回token值，使用jwt生成
        String token = memberService.login(loginVo);
        return R.ok().data("token", token);
    }
    // 注册
    @ApiOperation(value = "会员注册")
    @PostMapping("register")
    public R register(
            @ApiParam(name = "registerVo", value = "会员注册信息对象", required = true)
            @RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    //根据token获取用户信息
    @ApiOperation(value = "根据token获取登录信息")
    @GetMapping("auth/getLoginInfo")
    public R getLoginInfo(HttpServletRequest request){
        try {
            String memberId = JwtUtils.getMemberIdByJwtToken(request);
            LoginInfoVo loginInfoVo = memberService.getLoginInfo(memberId);
            return R.ok().data("item", loginInfoVo);
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"error");
         }
    }

    // 根据会员id获取用户信息
    @ApiOperation(value = "根据会员id获取用户信息")
    @PostMapping("getUserInfo/{memberId}")
    public UcenterMemberVo getUserInfo(
            @ApiParam(name = "memberId", value = "会员id", required = true)
            @PathVariable String memberId) {
        //根据用户id获取用户信息
        UcenterMember ucenterMember = memberService.getById(memberId);
        UcenterMemberVo memeberVo = new UcenterMemberVo();
        BeanUtils.copyProperties(ucenterMember,memeberVo);
        return memeberVo;
    }

    // 查询某一天的注册人数
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable String day) {
       Integer count = memberService.countRegisterByDay(day);
       return R.ok().data("countRegister", count);
    }
}

