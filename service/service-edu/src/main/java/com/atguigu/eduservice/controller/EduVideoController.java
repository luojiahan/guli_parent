package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.vo.VideoInfoVo;
import com.atguigu.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-05-07
 */
@Api(tags="课时管理")
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/video")
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    // 添加小节
    @ApiOperation(value = "新增课时")
    @PostMapping("addVideo")
    public R addVideo(
            @ApiParam(name = "videoInfoVo", value = "课时对象", required = true)
            @RequestBody VideoInfoVo videoInfoVo) {
        eduVideoService.saveVideoInfo(videoInfoVo);
        return R.ok();
    }

    // 根据ID查询小节
    @ApiOperation(value = "根据ID查询课时")
    @GetMapping("getVideoInfo/{VideoId}")
    public R getChapterInfo(
            @ApiParam(name = "VideoId", value = "课时ID", required = true)
            @PathVariable String VideoId) {
        VideoInfoVo videoInfoVo = eduVideoService.getVideoInfoById(VideoId);
        return R.ok().data("items", videoInfoVo);
    }

    // 更新小节
    @ApiOperation(value = "更新课时")
    @PostMapping("updateVideo")
    public R updateChapter(
            @ApiParam(name = "videoInfoVo", value = "课时对象", required = true)
            @RequestBody VideoInfoVo videoInfoVo) {
        eduVideoService.updateVideoInfoById(videoInfoVo);
        return R.ok();
    }

    // 删除小节
    // TODO 后面这个方法需要完善：删除小节的时候，同时把里面视频删除
    @ApiOperation(value = "根据ID删除课时")
    @DeleteMapping("{id}")
    public R deleteVideo(
            @ApiParam(name = "id", value = "课时ID", required = true)
            @PathVariable String id){
        boolean result = eduVideoService.removeVideoById(id);
        if(result){
            return R.ok();
        }else{
            return R.error().message("删除失败");
        }
    }
}

