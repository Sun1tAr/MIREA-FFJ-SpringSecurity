package my.learn.mireaffjspringsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.learn.mireaffjspringsecurity.dto.CreateUserDTO;
import my.learn.mireaffjspringsecurity.dto.JwtAuthorizationDTO;
import my.learn.mireaffjspringsecurity.dto.LoginUserDTO;
import my.learn.mireaffjspringsecurity.dto.ResetPasswordDTO;
import my.learn.mireaffjspringsecurity.entity.UserEntity;
import my.learn.mireaffjspringsecurity.service.AuthService;
import my.learn.mireaffjspringsecurity.service.JwtService;
import my.learn.mireaffjspringsecurity.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    @Transactional
    public JwtAuthorizationDTO registerUser(CreateUserDTO createUserDTO) {
        if (userService.loginIsRegistered(createUserDTO.getLogin())) {
            throw new RuntimeException("USER_ALREADY_REGISTERED");
        }
        createUserDTO.setPassword(
                passwordEncoder.encode(createUserDTO.getPassword())
        );
        UserEntity user = userService.createUser(createUserDTO);

        return new JwtAuthorizationDTO(jwtService.getJwtForUser(user));
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordDTO resetPasswordDTO) {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            throw new RuntimeException("USER_NOT_AUTHENTICATED");
        }

        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() == null) {
            log.error("Объект Principal должен быть не null");
            throw new RuntimeException("InternalServerErrorAppException");
        }

        String login = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        UserEntity user = userService.findUserByLogin(login);

        if (isSuccessfulAuth(login, resetPasswordDTO.getOldPassword())) {
            user.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
        } else {
            throw new RuntimeException("INCORRECT_LOGIN_OR_PASS");
        }

    }

    @Override
    public JwtAuthorizationDTO loginUser(LoginUserDTO loginUserDTO) {
        if (!userService.loginIsRegistered(loginUserDTO.getLogin())) {
            throw new RuntimeException("USER_NOT_FOUND");
        }

        if (!isSuccessfulAuth(loginUserDTO.getLogin(), loginUserDTO.getPassword())) {
            throw new RuntimeException("INCORRECT_LOGIN_OR_PASS");
        }

        return new JwtAuthorizationDTO(
                jwtService.getJwtForUser(
                        userService.findUserByLogin(loginUserDTO.getLogin())
                )
        );
    }


    @Override
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userService.loginIsRegistered(username)) {
            return userService.findUserByLogin(username);
        } else {
            throw UsernameNotFoundException.fromUsername(username);
        }
    }

    /** Аутентификация пользователя с возвратом статуса аутентификации
     *
     * @param login  логин
     * @param password пароль (сырой)
     * @return true если пользователь успешно аутентифицирован, иначе false
     */
    private boolean isSuccessfulAuth(String login, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                login,
                password
        );

        Authentication auth;
        try {
            auth = authenticationManager.authenticate(token);
        } catch (AuthenticationException e) {
            return false;
        }
        return auth.isAuthenticated();
    }
}
