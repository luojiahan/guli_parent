package com.atguigu.msmservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.msmservice.service.MsmService;
import com.atguigu.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

     @GetMapping(value = "send/{phone}")
     public R sendMsm(@PathVariable String phone) {
         String code = redisTemplate.opsForValue().get(phone);
         if(!StringUtils.isEmpty(code)) return R.ok();

         // 生成随机值，传递阿里云进行发送
         code = RandomUtil.getFourBitRandom();
         Map<String,Object> param = new HashMap<>();
         param.put("code", code);
         //调用service发送短信的方法
         boolean isSend = msmService.send(phone, "SMS_154950909", param);

         if(isSend) {
            redisTemplate.opsForValue().set(phone, code,5, TimeUnit.MINUTES);
            return R.ok();
         } else {
            return R.error().message("发送短信失败");
         }
     }
}
