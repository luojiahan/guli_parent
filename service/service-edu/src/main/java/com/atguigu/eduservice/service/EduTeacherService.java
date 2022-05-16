package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author atguigu
 * @since 2022-04-25
 */
public interface EduTeacherService extends IService<EduTeacher> {

    void pageTeacherCondition(Page<EduTeacher> pageParam, TeacherQuery teacherQuery);

    // 分页查询讲师功能
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageParam);
}
