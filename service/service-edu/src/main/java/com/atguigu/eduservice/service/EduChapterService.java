package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author atguigu
 * @since 2022-05-07
 */
public interface EduChapterService extends IService<EduChapter> {

    // 课程大纲列表显示 根据课程id进行查询
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    // 删除章节
    boolean deleteChapter(String chapterId);

    // 根据ID删除课程
    boolean removeByCourseId(String courseId);
}
