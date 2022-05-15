package com.atguigu.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {

    // 上传视频到阿里云
    String upload(MultipartFile file);

    // 删除阿里云视频
    void removeVideo(String videoId);

    // 批量删除阿里云视频
    void removeVideoList(List<String> videoIdList);
}
