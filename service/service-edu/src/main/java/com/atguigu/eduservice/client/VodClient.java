package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "service-vod", fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {
    // 定义调用的方法路径
    @DeleteMapping(value = "/eduvod/video/removeAliVod/{videoId}")
    public R removeVideo(@PathVariable("videoId") String videoId);
    // 这里用的RequestParam
    @DeleteMapping(value = "/eduvod/video/deleteBatch")
    public R removeVideoList(@RequestParam("videoIdList") List<String> videoIdList);
}
