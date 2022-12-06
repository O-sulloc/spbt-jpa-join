package com.study.spbtjpajoin.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenUtil {

    public static String createToken(String userName, String key, long expireTime){
        //유저네임, 암호화 풀 key, 유효 시간

        Claims claims = Jwts.claims();
        //key, value로 이루어진 형태인 Claims

        claims.put("userName", userName);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS384,key) //어떤 알고리즘 방식으로 암호할 것인지 지정해주는 것.
                .compact();
    }
}
