package my.learn.mireaffjspringsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import my.learn.mireaffjspringsecurity.constants.JwtClaims;
import my.learn.mireaffjspringsecurity.entity.UserEntity;
import my.learn.mireaffjspringsecurity.service.JwtService;
import my.learn.mireaffjspringsecurity.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final SecretKey secretKey;

    @Value("${ms.security.jwt.access-lifetime}")
    private Duration jwtLifetime;

    @Override
    public String getJwtForUser(UserEntity user) {
        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + jwtLifetime.toMillis());
        return JwtUtils.generateToken(user, expiresAt, secretKey);
    }

    @Override
    public boolean tokenIsValid(String token) {
        try {
            getLoginFromJwt(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getLoginFromJwt(String token) {
        return JwtUtils.getLoginFromToken(token, secretKey);
    }

    @Override
    public String getUuidFromJwt(String token) {
        return JwtUtils.getDataFromToken(JwtClaims.USER_UUID, token, secretKey);
    }
}
