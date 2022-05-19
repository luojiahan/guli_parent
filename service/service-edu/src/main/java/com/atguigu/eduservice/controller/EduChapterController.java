package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
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
@Api(tags = "章节管理")
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/chapter")
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    // 课程大纲列表显示 根据课程id进行查询
    @ApiOperation(value = "根据课程id进行查询")
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@ApiParam(name = "courseId", value = "课程ID", required = true)
                                 @PathVariable String courseId){

        List<ChapterVo> list = eduChapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("items", list);

    }

    // 新增章节
    @ApiOperation(value = "新增章节")
    @PostMapping("addChapter")
    public R addChapter(
            @ApiParam(name = "eduChapter", value = "章节对象", required = true)
            @RequestBody EduChapter eduChapter) {
        eduChapterService.save(eduChapter);
        return R.ok();
    }

    // 根据ID查询章节
    @ApiOperation(value = "根据ID查询章节")
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(
            @ApiParam(name = "chapterId", value = "章节ID", required = true)
            @PathVariable String chapterId) {
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return R.ok().data("items", eduChapter);
    }

    // 修改章节
    @ApiOperation(value = "修改章节")
    @PostMapping("updateChapter")
    public R updateChapter(
            @ApiParam(name = "eduChapter", value = "章节对象", required = true)
            @RequestBody EduChapter eduChapter) {
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    // 根据ID删除章节
    @ApiOperation(value = "根据ID删除章节")
    @DeleteMapping("{chapterId}")
    public R deleteChapter(
            @ApiParam(name = "chapterId", value = "章节ID", required = true)
            @PathVariable String chapterId) {
        boolean flag = eduChapterService.deleteChapter(chapterId);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

}

