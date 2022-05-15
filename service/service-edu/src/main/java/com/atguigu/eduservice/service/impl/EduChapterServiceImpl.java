package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptiohandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-05-07
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        // 1获取章节信息
        QueryWrapper<EduChapter> chaperWrapper = new QueryWrapper<>();
        chaperWrapper.eq("course_id", courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(chaperWrapper);

        // 2获取课时信息
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id", courseId);
        List<EduVideo> eduVideos = eduVideoService.list(videoWrapper);

        List<ChapterVo> finalChapters = new ArrayList<>();

        // 3封装章节vo数据
        for(int i = 0; i<eduChapters.size(); i++){
            EduChapter eduChapter = eduChapters.get(i);
            // 创建章节vo对象
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            finalChapters.add(chapterVo);

            // 4封装课时vo数据
            List<VideoVo> finalVideos = new ArrayList<>();
            for(int m = 0; m < eduVideos.size(); m++){
                EduVideo eduVideo = eduVideos.get(m);
                if (eduChapter.getId().equals(eduVideo.getChapterId())){
                    // 创建课时vo对象
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    finalVideos.add(videoVo);
                }
            }
            chapterVo.setChildren(finalVideos);
        }
        return finalChapters;
    }

    //删除章节
    @Override
    public boolean deleteChapter(String chapterId) {
        // 根据章节id查询小节
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id", chapterId);
        int count = eduVideoService.count(queryWrapper);

        if(count > 0){
            throw new GuliException(20001, "不能删除");
        } else {
            int res = baseMapper.deleteById(chapterId);
            return res > 0;
        }

    }

    // 根据ID删除课程
    @Override
    public boolean removeByCourseId(String courseId) {
        QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        int count = baseMapper.delete(queryWrapper);
        return count > 0;
    }
}
