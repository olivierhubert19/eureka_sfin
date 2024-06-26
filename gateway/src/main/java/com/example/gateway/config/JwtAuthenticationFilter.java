package com.example.gateway.config;

import com.example.gateway.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {
    private final TokenService tokenService;
    private final RestTemplate restTemplate;
    private final RedisTemplate<String,String> redisTemplate;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final String path = exchange.getRequest().getPath().toString();
        final String method = exchange.getRequest().getMethod().toString();
        System.out.println(path);
        if(path.contains("/api/login")||path.contains("/api/register")){
           return chain.filter(exchange);
        }
        final String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if(authHeader==null||!authHeader.startsWith("Bearer ")){
             exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
             return exchange.getResponse().setComplete();
        }
        final String jwt = authHeader.substring(7);
        if(!tokenService.isInValidToken(jwt)){
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        final String[] subject = tokenService.getSubjectFromToken(jwt).split(" ");
        final String role = subject[1];
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("path",path);
        params.add("method",method);
        params.add("role",role);
        HttpEntity<MultiValueMap<String,String>> requestEntity = new  HttpEntity<>(params);
        ResponseEntity<String> responseAuthen = restTemplate.postForEntity("/check",requestEntity,String.class);
        if(!responseAuthen.hasBody()) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.BAD_REQUEST);
            return response.setComplete();
        }else{
            if(responseAuthen.getStatusCode()==HttpStatus.OK){
                final String userid = subject[0];
                redisTemplate.opsForValue().set("User_authen_"+userid,responseAuthen.getBody());
                return chain.filter(exchange);
            }else {
                System.out.println(responseAuthen.getBody());
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
