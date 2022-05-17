package com.atguigu.eduservice.entity.chapter;

import lombok.Data;

@Data
public class VideoVo {

    private String id;
    private String title;
    private String videoSourceId; // 视频ID
    private Boolean isFree;

}
