package com.example.xcxgood.config;

import com.example.xcxgood.Entity.MinioEntity;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MinioEntity.class)
public class MinioConfig {

    @Autowired
    private MinioEntity minioEntity;

    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder()
                .endpoint(minioEntity.getEndPoint())
                .credentials(minioEntity.getAccessKey(), minioEntity.getSecretKey())
                .build();
    }
}
