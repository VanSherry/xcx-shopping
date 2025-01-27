package com.example.xcxgood.Entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioEntity {
    private String endPoint;
    private String accessKey;
    private String secretKey;
    private String bucket;

}
