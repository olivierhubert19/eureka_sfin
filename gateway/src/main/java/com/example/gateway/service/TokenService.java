package com.example.gateway.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class TokenService {
    final String TOKEN = "Token: ";
    @Value("${SECRET_KEY}")
    private String SECRET_KEY;
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtService jwtService;
    public boolean isInValidToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
            String[] userInfo = claims.getSubject().split(" ");
            if(userInfo[0]==null) return false;
            String token1Check = redisTemplate.opsForValue().get(TOKEN+userInfo[0]);
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

    public String getSubjectFromToken(String token){
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
            return claims.getSubject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
