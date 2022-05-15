package com.atguigu.vod.controller;

import com.atguigu.commonutils.R;
import com.atguigu.vod.service.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags="阿里云视频点播微服务")
@RestController
@CrossOrigin
@RequestMapping("/eduvod/video")
public class VodController {

    @Autowired
    private VodService vodService;

    // 上传视频到阿里云
    @ApiOperation(value = "上传视频到阿里云")
    @PostMapping("uploadAliVod")
    public R upload(@ApiParam(name = "file", value = "文件", required = true) MultipartFile file){
        String vodId = vodService.upload(file);
        return R.ok().message("视频上传成功").data("videoId", vodId);
    }

    // 删除阿里云视频
    @ApiOperation(value = "删除阿里云中的视频")
    @DeleteMapping("removeAliVod/{videoId}")
    public R removeVideo(
            @ApiParam(name = "videoId", value = "云端视频id", required = true)
            @PathVariable String videoId){
        vodService.removeVideo(videoId);
        return R.ok().message("视频删除成功");
    }

    // 批量删除阿里云视频
    @ApiOperation(value = "批量删除阿里云视频")
    @DeleteMapping("deleteBatch")
    public R removeVideoList(
            @ApiParam(name = "videoIdList", value = "云端视频id列表", required = true)
            @RequestParam("videoIdList") List videoIdList){
        vodService.removeVideoList(videoIdList);
        return R.ok().message("视频删除成功");
    }

}
