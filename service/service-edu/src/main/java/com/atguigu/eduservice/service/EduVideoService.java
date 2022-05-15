package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.vo.VideoInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author atguigu
 * @since 2022-05-07
 */
public interface EduVideoService extends IService<EduVideo> {

    // 添加小节
    void saveVideoInfo(VideoInfoVo videoInfoVo);

    // 根据ID查询小节
    VideoInfoVo getVideoInfoById(String videoId);

    // 更新小节
    void updateVideoInfoById(VideoInfoVo videoInfoVo);

    // 删除小节
    boolean removeVideoById(String id);

    // 根据ID删除课程
    boolean removeByCourseId(String courseId);
}
