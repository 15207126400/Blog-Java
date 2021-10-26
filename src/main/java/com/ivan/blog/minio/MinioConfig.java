package com.ivan.blog.minio;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ivan
 * @date 2021/10/22 11:58
 * @Description minio配置类
 */
@Configuration
public class MinioConfig {

    @Value("${spring.minio.endpoint}")
    private String endpoint;
    @Value("${spring.minio.accessKey}")
    private String accessKey;
    @Value("${spring.minio.secretKey}")
    private String secretKey;

    @Bean
    public MinioClient minioClient() {
        MinioClient client = new MinioClient.Builder().endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();

        return client;
    }
}
