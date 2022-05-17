package com.atguigu.eduservice.client;

import com.atguigu.commonutils.UcenterMemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name = "service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {

    //根据用户id获取用户信息
    @PostMapping("/educenter/member/getInfoUc/{memberId}")
    public UcenterMemberVo getInfoUc(@PathVariable("memberId") String memberId);
}