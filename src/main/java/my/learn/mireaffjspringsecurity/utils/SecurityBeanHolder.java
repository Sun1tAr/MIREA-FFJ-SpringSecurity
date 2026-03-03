package my.learn.mireaffjspringsecurity.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;

@Component
public class SecurityBeanHolder {

    /**
     * Получение AuthenticationManager через статический метод реализовано
     * для исключения циклической зависимости:
     * AuthenticationManager <----> UserDetailsService
     */
    @Getter
    private static AuthenticationManager authenticationManager;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager am) {
        authenticationManager = am;
    }

}
