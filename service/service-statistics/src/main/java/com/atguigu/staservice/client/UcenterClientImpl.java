package com.atguigu.staservice.client;

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Service;

@Service
public class UcenterClientImpl implements UcenterClient{
    @Override
    public R countRegister(String day) {
        return R.error().message("会员中心调用失败");
    }
}
