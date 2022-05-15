package com.atguigu.eduservice.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.vo.VideoInfoVo;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptiohandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-05-07
 */
@Service
@Slf4j
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;
    
    // 添加小节
    @Override
    public void saveVideoInfo(VideoInfoVo videoInfoVo) {
        EduVideo video = new EduVideo();
        BeanUtils.copyProperties(videoInfoVo, video);
        int insert = baseMapper.insert(video);
        if (insert == 0) {
            throw new GuliException(20001, "添加课时信息失败");
        }
    }

    // 根据ID查询小节
    @Override
    public VideoInfoVo getVideoInfoById(String videoId) {
        // 查询数据
        EduVideo video = baseMapper.selectById(videoId);
        if(video == null){
            throw new GuliException(20001, "数据不存在");
        }
        // 创建Vo对象
        VideoInfoVo videoInfoVo = new VideoInfoVo();
        BeanUtils.copyProperties(video, videoInfoVo);

        return videoInfoVo;
    }

    // 更新小节
    @Override
    public void updateVideoInfoById(VideoInfoVo videoInfoVo) {
        EduVideo video = new EduVideo();
        BeanUtils.copyProperties(videoInfoVo, video);
        int result = baseMapper.updateById(video);
        if(result == 0){
            throw new GuliException(20001, "课时信息保存失败");
        }
    }

    // 根据小节id删除
    @Override
    public boolean removeVideoById(String id) {
        // 查询云端视频id
        EduVideo video = baseMapper.selectById(id);
        String videoSourceId = video.getVideoSourceId();

        if (!StringUtils.isEmpty(videoSourceId)) {
            R result = vodClient.removeVideo(videoSourceId);
            if (result.getCode() == 20001) {
                throw new GuliException(20001, "删除视频失效， 熔断器..");
            }
        }

        int result = baseMapper.deleteById(id);
        return result > 0;
    }

    // 根据课程ID删除Video
    @Override
    public boolean removeByCourseId(String courseId) {
        //根据课程id查询所有视频列表
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        queryWrapper.select("video_source_id");
        List<EduVideo> videoList = baseMapper.selectList(queryWrapper);
        //得到所有视频列表的云端原始视频id
        List<String> videoSourceIdList = new ArrayList<>();
        for (int i = 0; i < videoList.size(); i++) {
            EduVideo video = videoList.get(i);
            String videoSourceId = video.getVideoSourceId();
            if(!StringUtils.isEmpty(videoSourceId)){
                videoSourceIdList.add(videoSourceId);
            }
        }
        //调用vod服务删除远程视频
        if(videoSourceIdList.size() > 0){
            vodClient.removeVideoList(videoSourceIdList);
        }
        //删除video表示的记录
        QueryWrapper<EduVideo> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("course_id", courseId);
        int count = baseMapper.delete(queryWrapper2);
        return count > 0;
    }
}
