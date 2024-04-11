package com.example.gateway.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.Date;
@Service
@Component
@Slf4j
public class JwtService {
    private final String SECRET_KEY = "1239847129038471293847129384712093847129387412912903847";

    public boolean validateJwtToken(String jwt) {
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwt).getBody();
            Date endDate = claims.getExpiration();
            if(endDate==null) return false;
            System.out.println(endDate);
            return endDate.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

}
