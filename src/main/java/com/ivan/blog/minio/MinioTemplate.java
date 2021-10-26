package com.ivan.blog.minio;

import com.ivan.blog.constants.BlogConstants;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author Ivan
 * @date 2021/10/26 11:53
 * @Description
 */
@Slf4j
@Component
@AllArgsConstructor
public class MinioTemplate {

    private final MinioClient minioClient;

    /**
     * 上传文件
     * @param uploadFile
     * @return
     */
    public String upload(String bucket, MultipartFile uploadFile){
        String suffix = uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf("."));
        String filename = UUID.randomUUID() + suffix;
        try{
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(filename)
                    .stream(uploadFile.getInputStream(), uploadFile.getInputStream().available(), -1)
                    .contentType(uploadFile.getContentType())
                    .build());
        }catch (Exception e){
            e.printStackTrace();
        }

        return filename;
    }

    /**
     * 删除文件
     * @param filename
     */
    public void delete(String bucket, String filename){
        try{
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucket)
                    .object(filename)
                    .build());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 构造访问路径
     * @param filename
     * @return
     */
    public String constructingAccessUrl(String bucket, String filename){
        String path = BlogConstants.MINIO_MAIN_PATH + bucket + BlogConstants.ROOT + filename;
        return path;
    }

    /**
     * 获取图片预览URL(最大时效: 7天)
     * @param filename
     * @return
     */
    public String preview(String bucket, String filename){
        String url = null;
        try{
            url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucket)
                    .object(filename)
                    .build());
        }catch (Exception e){
            e.printStackTrace();
        }
        return url;
    }
}
