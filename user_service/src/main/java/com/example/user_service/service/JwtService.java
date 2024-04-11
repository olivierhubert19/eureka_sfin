package com.example.user_service.service;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;

@Service
public class JwtService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    final String SECRET_KEY = "1239847129038471293847129384712093847129387412912903847";
    final String TOKEN = "Token: ";

    public String generateToken() {
        String jwt = Jwts
                .builder()
                .setIssuedAt(new Date())
                .setExpiration( new Date(new Date().getTime() + 1000 * 60 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
        redisTemplate.opsForValue().set(TOKEN+jwt.substring(1,9),jwt);
        return jwt;
    }


    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);

    }
}

