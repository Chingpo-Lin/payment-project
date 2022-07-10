package com.example.paymentdemo;

import com.example.paymentdemo.domain.User;
import com.example.paymentdemo.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;

public class CommonTest {

    @Test
    public void testGeneJwt() {

        User user = new User();
        user.setId(999);
        user.setHeadImg("trytry");
        user.setName("xx");

        String token = JwtUtils.geneJsonWebToken(user);
        System.out.println(token);
    }

    @Test
    public void testCheck() {

        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ" +
                "wYXltZW50IiwiaWQiOjk5OSwibmFtZSI6Inh4IiwiaW1nIjoid" +
                "HJ5dHJ5IiwiaWF0IjoxNjU2NDU5Njc3LCJleHAiOjE2NTcwNjQ0Nzd9" +
                ".ea3fK1FdbSdOTr3KniT5l8T1cYkpJVTjCXRhpUVfFEY";

        Claims claims = JwtUtils.checkJWT(token);
        if (claims != null) {
            String name = (String) claims.get("name");
            int id = (Integer) claims.get("id");
            String img = (String) claims.get("img");
            System.out.println("name" + name);
            System.out.println("id" + id);
            System.out.println("img" + img);
        } else {
            System.out.println("fail");
        }
    }
}
