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
    final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    final String TOKEN = "Token: ";

    private final RedisTemplate<String, String> redisTemplate;
    private final JwtService jwtService;
    public boolean isInValidToken(String token) {
        System.out.println("Check");
        try {
            String token1Check = redisTemplate.opsForValue().get(TOKEN+token.substring(1,9));
            System.out.println(token1Check);
            if (token1Check == null) {
                System.out.println("Cannot find token");
                return false;
            }
            if(jwtService.validateJwtToken(token)){
                return true;
            }else {
                System.out.println("Invalid");
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
