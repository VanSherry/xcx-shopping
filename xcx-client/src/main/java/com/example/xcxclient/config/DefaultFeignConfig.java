package com.example.xcxclient.config;

import com.example.xcxcommon.context.UserContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;

public class DefaultFeignConfig {
    @Bean
    public RequestInterceptor userIdRequestInterceptor(){
        return new RequestInterceptor() {
            public void apply(RequestTemplate requestTemplate) {
                Long userId = UserContext.getUser();
                if (userId != null){
                    requestTemplate.header("userId",userId.toString());
                }
            }
        };
    }
}
