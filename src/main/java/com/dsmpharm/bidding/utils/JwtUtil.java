package com.dsmpharm.bidding.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * JAVA-JWT工具类
 */
public class JwtUtil {

    public final static String ACCOUNT = "account";

    private String key = "aasgrant";

    // 有效期 10 秒
    //private long expirationTime = 60;
    private long expirationTime = 36000000;

    // 生成JWT
    public String createJWT(String id, String subject) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setId(id)
                .setSubject(subject)
                .setIssuedAt(now)
                .claim(JwtUtil.ACCOUNT, subject)
                .signWith(SignatureAlgorithm.HS256, key);
        builder.setExpiration(new Date(System.currentTimeMillis() + (expirationTime * 1000)));
        return builder.compact();
    }

    // 解析JWT
    public Claims parseJWT(String jwtStr) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwtStr)
                .getBody();
    }
    // 解析JWT
    public String getUserId(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody().getId();
    }
}
