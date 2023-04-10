package com.fall.securitydemo02.utils;

import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * @author FAll
 * @date 2023/4/4 18:08
 */
public class JWTUtil {
    public static final Long JWT_TTL = 60 * 60 * 1000L;

    public static final String JWT_KEY = "fall";

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public static String createJWT(String subject) {
        JwtBuilder builder = getJwtBuilder(subject,null,getUUID());
        return builder.compact();
    }

    public static String createJWT(String subject, Long ttlMillis) {
         JwtBuilder builder = getJwtBuilder(subject,ttlMillis,getUUID());
         return builder.compact();
    }

    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        if(ttlMillis==null) {
            ttlMillis = JWTUtil.JWT_TTL;
        }
        long expMillis = nowMillis + JWT_TTL;
        Date expDate = new Date(expMillis);

        return Jwts.builder()
                .setId(uuid)            // 唯一id
                .setSubject(subject)    // 主题
                .setIssuer("fall")      // 签发者
                .setIssuedAt(now)       // 签发时间
                .signWith(signatureAlgorithm,secretKey) // 使用HS256算法结合密钥进行签名
                .setExpiration(expDate);

    }

    public static String createJWT(String id, String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id);
        return builder.compact();
    }

    public static SecretKey generalKey(){
        byte[] encodedKey = Base64.getDecoder().decode(JWTUtil.JWT_KEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    public static Claims parseJWT(String jwt) {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

}
