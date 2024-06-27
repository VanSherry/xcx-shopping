package com.example.xcxcart;

import com.example.xcxclient.config.DefaultFeignConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.xcxclient.client", defaultConfiguration = DefaultFeignConfig.class)
public class XcxCartApplication {

    public static void main(String[] args) {
        SpringApplication.run(XcxCartApplication.class, args);
    }

}
