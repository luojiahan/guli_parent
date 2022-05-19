package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.exceptiohandler.GuliException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;


/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-04-25
 */

@Api(tags="讲师管理")
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    EduTeacherService teacherService;

    /**
     * 查看讲师表所有数据
     * rest风格
     */
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher() {
        //调用service的方法实现查询所有的操作
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    /**
     * 讲师逻辑删除功能
     */
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}") //id需要通过路径进行传递
    public R removeById(
            @ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
    /**
     * 分页查询讲师列表
     */
    @ApiOperation(value = "分页查询讲师列表")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(
            @ApiParam(name = "current", value = "当前页码", required = true) @PathVariable long current,
            @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable long limit) {
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);

        try {
            int a = 10/0;
        }catch(Exception e) {
            throw new GuliException(20001, "出现自定义异常");
        }

        //调用方法实现分页,调用方法的时候，底层封装把分页所有数据封装到pageTeacher对象里面
        teacherService.page(pageTeacher, null);

        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();

        //返回方法二:
        return R.ok().data("total", total).data("rows", records);
    }

    /**
     * 条件查询带分页的方法
     * @return
     */
    @ApiOperation(value = "条件查询")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(
            @ApiParam(name = "current", value = "当前页码", required = true) @PathVariable Long current,
            @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit,
            @ApiParam(name = "teacherQuery", value = "查询对象", required = false) @RequestBody TeacherQuery teacherQuery) {
        Page<EduTeacher> pageTeacher= new Page<>(current, limit);
        teacherService.pageTeacherCondition(pageTeacher, teacherQuery);

        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();

        return R.ok().data("total", total).data("rows", records);
    }

    /**
     * 添加讲师接口
     */
    @ApiOperation(value = "添加讲师接口")
    @PostMapping("addTeacher")
    public R addTeacher(
            @ApiParam(name = "teacher", value = "讲师对象", required = true) @RequestBody EduTeacher eduTeacher) {
        boolean res = teacherService.save(eduTeacher);
        if(res) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 根据id查询教师信息
     */
    @ApiOperation(value = "根据id查询教师信息")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(
            @ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id) {

        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher", eduTeacher);

    }
    /**
     * 根据id修改教师信息
     */
    /**
     * 根据id修改教师信息
     */
    @ApiOperation(value = "根据id修改教师信息")
    @PostMapping("updateTeacher")
    public R updateTeacher(@ApiParam(name = "eduTeacher", value = "讲师对象", required = true) @RequestBody EduTeacher eduTeacher) {

        boolean res = teacherService.updateById(eduTeacher);
        if(res) {
            return R.ok();
        } else {
            return R.error();
        }
    }

}

