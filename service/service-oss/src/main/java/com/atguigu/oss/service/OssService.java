package com.atguigu.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 阿里OSS 服务类
 * </p>
 *
 * @author atguigu
 * @since 2022-04-28
 */
public interface OssService {

    /**
     * 头像文件上传至阿里云
     * @param file
     * @return
     */
    String uploadFileAvatar(MultipartFile file);
}
