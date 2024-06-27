package com.example.xcxorder;

import com.example.xcxclient.config.DefaultFeignConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.xcxclient.client", defaultConfiguration = DefaultFeignConfig.class)
public class XcxOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(XcxOrderApplication.class, args);
    }

}
