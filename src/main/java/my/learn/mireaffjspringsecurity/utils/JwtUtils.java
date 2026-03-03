package my.learn.mireaffjspringsecurity.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import my.learn.mireaffjspringsecurity.constants.JwtClaims;
import my.learn.mireaffjspringsecurity.entity.UserEntity;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    public static String generateToken(UserEntity user,
                                       Date expiresAt,
                                       SecretKey secretKey) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaims.LOGIN, user.getLogin());
        claims.put(JwtClaims.EXPIRATION, expiresAt.getTime());
        claims.put(JwtClaims.USER_UUID, user.getUuid());

        return Jwts.builder()
                .subject(user.getLogin())
                .claims(claims)
                .expiration(expiresAt)
                .signWith(secretKey)
                .compact();
    }

    private static Claims getClaimsFromToken(String token,
                                             SecretKey secretKey) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();


    }

    public static String getLoginFromToken(String token,
                                           SecretKey secretKey) {
        return getClaimsFromToken(token, secretKey).getSubject();
    }


    public static String getDataFromToken(String claim,
                                          String token,
                                          SecretKey secretKey) {
        Claims claims = getClaimsFromToken(token, secretKey);
        return claims.get(claim, String.class);
    }
}
