package com.atguigu.eduorder.client;

import com.atguigu.commonutils.orderVo.UcenterMemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    // 根据会员id获取用户信息
    @PostMapping("/educenter/member/getUserInfo/{memberId}")
    public UcenterMemberVo getUserInfo(@PathVariable("memberId") String memberId);

}
