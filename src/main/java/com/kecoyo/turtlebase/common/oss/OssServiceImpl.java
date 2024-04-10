package com.kecoyo.turtlebase.common.oss;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;

import cn.hutool.core.io.FileUtil;

@Component
public class OssServiceImpl {

    @Autowired
    private OssProperties ossProperties;

    /**
     * 从OSS上下载文件到本地文件系统
     *
     * @param objectName
     * @param localFilePath
     */
    public void downloadFile(String objectName, String localFilePath) {
        // 确保下载文件目录存在
        FileUtil.mkParentDirs(localFilePath);
        // 创建OSSClient实例
        String endpoint = ossProperties.getInternal() ? ossProperties.getInternalEndpoint()
                : ossProperties.getEndpoint();
        OSS ossClient = new OSSClientBuilder().build(endpoint, ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret());
        try {
            // 从OSS上下载文件到本地文件系统
            ossClient.getObject(new GetObjectRequest(ossProperties.getBucketName(), objectName),
                    new File(localFilePath));

            System.out.println("文件下载成功！");
        } catch (Exception e) {
            System.err.println("文件下载失败：" + e.getMessage());
            throw e;
        } finally {
            // 关闭OSSClient实例以释放资源
            ossClient.shutdown();
        }
    }

    /**
     * 上传本地文件到OSS
     *
     * @param objectName
     * @param localFilePath
     */
    public void uploadFile(String objectName, String localFilePath) {
        // 创建OSSClient实例
        String endpoint = ossProperties.getInternal() ? ossProperties.getInternalEndpoint()
                : ossProperties.getEndpoint();
        OSS ossClient = new OSSClientBuilder().build(endpoint, ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret());
        try {
            ossClient.putObject(ossProperties.getBucketName(), objectName, new File(localFilePath));
        } catch (Exception e) {
            System.out.println("文件上传失败：" + e.getMessage());
            throw e;
        } finally {
            ossClient.shutdown();
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
