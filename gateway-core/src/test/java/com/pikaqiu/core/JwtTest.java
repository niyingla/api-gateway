package com.pikaqiu.core;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * com.pikaqiu
 *
 * @Author: SongJian
 * @Create: 2023/6/13 10:26
 * @Version:
 * @Describe:
 */
public class JwtTest {

    @Test
    public void jwt() {
        String secureKey = "pikaqiu";

        String token = Jwts.builder()
                .setSubject("10000")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, secureKey)
                .compact();
        System.out.println(token);
    }
}
