package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "讲师前台页面")
@RestController
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    /**
     * 分页查询讲师功能
     * @param page 当前页码
     * @param limit 每页记录数
     * @return 带分页的讲师map
     */
    @ApiOperation(value = "分页讲师列表")
    @GetMapping(value = "getTeacherFrontList/{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {

        Page<EduTeacher> pageParam = new Page<>(page, limit);
        Map<String, Object> map = teacherService.getTeacherFrontList(pageParam);

        return R.ok().data(map);
    }

    /**
     * 根据讲师id查询当前讲师的课程列表
     * @param teacherId 讲师id
     * @return 讲师和课程的JSON列表
     */
    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping(value = "getTeacherInfo/{teacherId}")
    public R getById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String teacherId) {
        // 查询讲师信息
        EduTeacher teacher = teacherService.getById(teacherId);
        // 根据讲师id查询这个讲师的课程列表
        List<EduCourse> courseList = courseService.selectByTeacherId(teacherId);
        return R.ok().data("teacher", teacher).data("courseList", courseList);
    }
}
