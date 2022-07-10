package com.example.paymentdemo.utils;

import com.example.paymentdemo.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtils {

    public static final String SUBJECT = "payment";

    public static Long EXPIRE = 1000L * 60 * 60 * 24 * 7; // a week to expire

    // secret key
    public static final String APPSECRET = "xd666";

    /**
     * generate jwt
     * @param user
     * @return
     */
    public static String geneJsonWebToken(User user) {

        if (user == null || user.getId() == null || user.getName() == null ||
                user.getHeadImg() == null) {
            return null;
        }
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("id", user.getId())
                .claim("name", user.getName())
                .claim("img", user.getHeadImg())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256, APPSECRET).compact();

        return token;

    }

    /**
     * check token
     * @return
     */
    public static Claims checkJWT(String token) {
        try {
            final Claims claims = Jwts.parser().setSigningKey(APPSECRET)
                    .parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
