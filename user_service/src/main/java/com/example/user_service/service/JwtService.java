package com.example.user_service.service;


import com.example.user_service.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;

@Service
public class JwtService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Value("${SECRET_KEY}")
    private String SECRET_KEY;
    final String TOKEN = "Token: ";

    public String generateToken(User user) {
        String jwt = Jwts
                .builder()
                .setSubject(user.getId()+" "+user.getRole())
                .setIssuedAt(new Date())
                .setExpiration( new Date(new Date().getTime() + 1000 * 60 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
        redisTemplate.opsForValue().set(TOKEN+user.getId(),jwt);
        return jwt;
    }


    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);

    }
}

