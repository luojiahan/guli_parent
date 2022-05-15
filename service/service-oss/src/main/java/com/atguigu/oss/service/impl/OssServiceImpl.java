package com.atguigu.oss.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.PutObjectRequest;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * <p>
 * 阿里OSS 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-04-28
 */
@Service
public class OssServiceImpl implements OssService {

    /**
     * 头像文件上传至阿里云
     * @param file
     * @return
     */
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // 工具类获取值
        String endPoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;

        String uploadUrl = null;

        try {
            // 创建OSSClient实例
            OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
            if (!ossClient.doesBucketExist(bucketName)) {
                //创建bucket
                ossClient.createBucket(bucketName);
                //设置oss实例的访问权限：公共读
                ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            }

            // 获取文件名称
            String originalFilename = file.getOriginalFilename();
            // 1在文件名称上添加随机唯一的值
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
            String ossFileName = uuid  + fileType;


            // 2把文件按照日期分类
            String dataPath = new DateTime().toString("yyyy/MM/dd");
            ossFileName = dataPath + "/" + ossFileName;



            // 获取上传文件流
            InputStream inputStream = file.getInputStream();

            // 调用oss方法实现上传
            // 第一个参数：bucketName
            // 第二个参数：上传到oss文件路径和名称 aa/bb/1.png
            // 第三个参数：上传文件的输入流
            ossClient.putObject(bucketName, ossFileName, inputStream);

            // 关闭对象
            ossClient.shutdown();

            // 把上传之后的文件路径返回
            // 需要把上传阿里云oss路径手动拼接出来
            // https://tec-file.oss-cn-beijing.aliyuncs.com/avatar/Test.png
            uploadUrl = "https://" + bucketName + "." + endPoint + "/" + ossFileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uploadUrl;
    }
}
