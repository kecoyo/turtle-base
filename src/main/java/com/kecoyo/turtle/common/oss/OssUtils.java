package com.kecoyo.turtle.common.oss;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OssUtils {

    @Autowired
    private OssProperties ossProperties;

    @Autowired
    private OSSClient ossClient;

    /**
     * 从OSS上下载文件到本地文件系统
     *
     * @param objectName
     * @param localFilePath
     */
    public void downloadFile(String objectName, String localFilePath) {
        // 确保下载文件目录存在
        FileUtil.mkParentDirs(localFilePath);
        try {
            // 从OSS上下载文件到本地文件系统
            ossClient.getObject(new GetObjectRequest(ossProperties.getBucketName(), objectName),
                    new File(localFilePath));

            log.debug("文件下载成功！");
        } catch (Exception e) {
            log.error("文件下载失败：" + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 上传本地文件到OSS
     *
     * @param objectName
     * @param localFilePath
     */
    public void uploadFile(String objectName, String localFilePath) {
        try {
            ossClient.putObject(ossProperties.getBucketName(), objectName, new File(localFilePath));
            log.debug("文件上传成功！");
        } catch (Exception e) {
            log.error("文件上传失败：" + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 上传本地目录到OSS
     *
     * @param objectDir
     * @param localDir
     */
    public void uploadDir(String objectDir, String localDir) {
        File dir = new File(localDir);
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    String name = file.getName();
                    if (file.isDirectory()) {
                        uploadDir(objectDir + name + "/", file.getAbsolutePath()); // 递归遍历子目录
                    } else {
                        uploadFile(objectDir + name, file.getAbsolutePath());
                    }
                }
            }
        }
    }

}
