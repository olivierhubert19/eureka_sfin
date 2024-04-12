package com.example.gateway.service;

import com.example.gateway.model.Token;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@RequiredArgsConstructor
public class TokenService {
    final String TOKEN = "Token: ";

    private final RedisTemplate<String, String> redisTemplate;
    private final JwtService jwtService;
    public boolean isInValidToken(String token) {
        try {
            String token1Check = redisTemplate.opsForValue().get(TOKEN+token.substring(1,9));
            System.out.println(token1Check);
            if (token1Check == null) {
                return false;
            }
            return jwtService.validateJwtToken(token);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
