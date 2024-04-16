package com.example.gateway.config;

import com.example.gateway.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {
    private final TokenService tokenService;
    private final RestTemplate restTemplate;
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
        final String role = tokenService.getRoleFromToken(jwt);
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
        }
        if(responseAuthen.getStatusCode()==HttpStatus.OK) return chain.filter(exchange);
        System.out.println(responseAuthen.getBody());
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
