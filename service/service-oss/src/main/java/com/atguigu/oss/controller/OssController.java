package com.atguigu.oss.controller;

import com.atguigu.commonutils.R;
import com.atguigu.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 阿里OSS 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-04-28
 */
@Api(tags="阿里云OSS")
@RestController
@CrossOrigin
@RequestMapping("/eduoss/file/upload")
public class OssController {

    @Autowired
    private OssService ossService;

    /**
     * 头像文件上传阿里云OSS
     * @param file 上传的文件
     * @return 返回阿里OSS文件路径
     */
    @ApiOperation(value = "头像文件上传阿里云OSS")
    @PostMapping
    public R uploadOssFile(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file) {
        // 获取上传文件MultipartFile
        // 返回oss上传路径
        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url", url);
    }
}
