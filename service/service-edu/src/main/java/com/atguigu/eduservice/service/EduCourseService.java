package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.frontVo.CourseFrontVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author atguigu
 * @since 2022-05-07
 */
public interface EduCourseService extends IService<EduCourse> {

    // 添加课程基本信息的方法
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    // 根据课程Id查询课程基本信息
    CourseInfoVo getCourseInfo(String courseId);

    // 更新课程信息
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    // 根据课程id获取课程确认信息
    CoursePublishVo getCoursePublishInfo(String id);

    // 根据id发布课程
    void publishCourse(String id);

    // 分页课程列表展示
    void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery);

    // 根据ID删除课程
    boolean removeCourseById(String id);

    // 根据讲师id查询当前讲师的课程列表
    List<EduCourse> selectByTeacherId(String teacherId);

    //前台多条件分页查询
    Map<String, Object> getCourseFrontInfo(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);
}
