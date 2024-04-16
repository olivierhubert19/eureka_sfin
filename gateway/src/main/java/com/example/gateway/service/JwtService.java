package com.example.gateway.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.Date;
@Service
@Component
@Slf4j
public class JwtService {
    @Value("${SECRET_KEY}")
    private String SECRET_KEY;

    public boolean validateJwtToken(String jwt) {
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwt).getBody();
            Date endDate = claims.getExpiration();
            if(endDate==null) return false;
            return endDate.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

}
