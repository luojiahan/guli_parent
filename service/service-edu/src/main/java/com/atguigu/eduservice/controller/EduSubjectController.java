package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-05-05
 */
@Api(tags = "课程分类管理")
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/subject")
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    /**
     * 添加课程分类
     * 获取上传的文件路径，把文件内容读取出来
     * @param file 文件路径
     * @return
     */
    @ApiOperation(value = "批量添加课程")
    @PostMapping("addSubject")
    public R addSubject(@ApiParam(name = "file", value = "文件", required = true) MultipartFile file) {
        // 上传过来excel文件
        eduSubjectService.saveSubject(file, eduSubjectService);
        return R.ok().message("添加课程分类成功");
    }

    /**
     * 课程分类列表（树形）
     * @return
     */
    @ApiOperation(value = "课程分类列表")
    @GetMapping("getAllSubject")
    public R getAllSubject() {
        // list集合泛型是一级分类
        List<OneSubject> list = eduSubjectService.getAllOneTwoSubject();
        return R.ok().data("items", list);
    }
}

