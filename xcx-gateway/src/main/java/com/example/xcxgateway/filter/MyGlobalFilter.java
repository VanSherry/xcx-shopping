package com.example.xcxgateway.filter;

import com.example.xcxcommon.constant.JWTConstant;
import com.example.xcxcommon.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
public class MyGlobalFilter implements GlobalFilter, Ordered {
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        //如果是登录接口，则放行
        if(request.getURI().getPath().contains("/register") || request.getURI().getPath().contains("/login")){
            return chain.filter(exchange);
        }
        List<String> token = request.getHeaders().get("token");
        String jwt = null;
        if(token != null && !token.isEmpty()){
            jwt = token.get(0);
        }

        Claims claims;
        try {
            claims = JWTUtils.parseJWT(JWTConstant.SECRETKEY, jwt);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        log.info("//" + claims.toString());

        Long id = (Long) claims.get("用户id");

        ServerWebExchange newExchange = exchange.mutate()
                .request(builder -> builder.header("userId", String.valueOf(id)))
                .build();
        return chain.filter(newExchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
