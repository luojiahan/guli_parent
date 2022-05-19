package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-05-07
 */
@Api(tags = "课程信息管理")
@RestController
@RequestMapping("/eduservice/course")
//@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    // 课程列表
    @GetMapping("getCourseList")
    public R getCourseList() {
        List<EduCourse> list = eduCourseService.list(null);
        return R.ok().data("items", list);
    }

    // TODO 完善条件查询带分页
    // 分页课程列表显示
    @ApiOperation(value = "分页课程列表")
    @GetMapping("pageCourse/{page}/{limit}")
    public R pageQuery(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
                    CourseQuery courseQuery){
        Page<EduCourse> pageParam = new Page<>(page, limit);
        eduCourseService.pageQuery(pageParam, courseQuery);
        List<EduCourse> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    // 根据ID删除课程
    @ApiOperation(value = "根据ID删除课程")
    @DeleteMapping("deleteCourse/{id}")
    public R removeById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){
        boolean result = eduCourseService.removeCourseById(id);
        if(result){
            return R.ok();
        }else{
            return R.error().message("删除失败");
        }
    }
    // 添加课程基本信息的方法
    @ApiOperation(value = "添加课程基本信息")
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@ApiParam(name = "courseInfoVo", value = "课程信息封装", required = true)
                               @RequestBody CourseInfoVo courseInfoVo) {
        // 返回课程ID，为后面添加大纲使用
        String id = eduCourseService.saveCourseInfo(courseInfoVo);

        return R.ok().data("courseId", id);
    }

    // 根据课程Id查询课程基本信息
    @ApiOperation(value = "根据课程Id查询课程基本信息")
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@ApiParam(name = "courseId", value = "课程Id", required = true)
                               @PathVariable String courseId){

        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(courseId);
        return R.ok().data("items", courseInfoVo);

    }

    // 更新课程信息
    @ApiOperation(value = "更新课程信息")
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(
            @ApiParam(name = "courseInfoVo", value = "课程基本信息", required = true)
            @RequestBody CourseInfoVo courseInfoVo){
        eduCourseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    // 根据ID获取课程发布信息
    @ApiOperation(value = "根据ID获取课程发布信息")
    @GetMapping("getCoursePublishInfo/{id}")
    public R getCoursePublishInfo(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id) {

        CoursePublishVo coursePublishVo = eduCourseService.getCoursePublishInfo(id);
        return R.ok().data("items", coursePublishVo);
    }
    // 根据id发布课程
    @ApiOperation(value = "根据id发布课程")
    @PostMapping("publishCourse/{id}")
    public R publishCourse(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){
        eduCourseService.publishCourse(id);
        return R.ok();
    }
}

