package com.wl.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.wl.oss.service.OssServicce;
import com.wl.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssServicce {
    //上传头像到oss
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        //获取阿里云存储相关常量
        String endPoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        //获取文件名称
        //为了解决在上传文件过程中会出现的同名覆盖问题，可以有以下2中解决方案
        //第一种是在文件名称中加入随机的唯一的值
        String filename = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        filename = uuid + filename;
        //第二种是把文件按照日期进行分类
        //2024/3/01/01.jpg这种方式
        //获取当前日期
        String datePath = new DateTime().toString("yyyy/MM/dd");
        filename = datePath+"/"+filename;


        try {
            OSS ossClient = new OSSClientBuilder().build(endPoint,accessKeyId,accessKeySecret);
            InputStream inputStream = file.getInputStream();
            ossClient.putObject(bucketName,filename,inputStream);
            ossClient.shutdown();
            //把上传到阿里云oss的路径手动拼接出来
            String url = "https://"+bucketName+"."+endPoint+"/"+filename;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }
}
