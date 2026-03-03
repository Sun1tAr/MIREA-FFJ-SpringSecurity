package my.learn.mireaffjspringsecurity.service;

import my.learn.mireaffjspringsecurity.dto.CreateUserDTO;
import my.learn.mireaffjspringsecurity.dto.JwtAuthorizationDTO;
import my.learn.mireaffjspringsecurity.dto.LoginUserDTO;
import my.learn.mireaffjspringsecurity.dto.ResetPasswordDTO;
import my.learn.mireaffjspringsecurity.entity.UserEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
public interface AuthService {

    /** Регистрация пользователя с указанными данными и выдачей JWT
     * @param createUserDTO данные для создания пользователя (в т.ч. учетные)
     * @return данные для аутентификации пользователя посредством JWT
     */
    JwtAuthorizationDTO registerUser(CreateUserDTO createUserDTO);

    /** Смена пароля с контролем старого
     * @param resetPasswordDTO данные для обновления пароля
     */
    void resetPassword(ResetPasswordDTO resetPasswordDTO);

    /** Аутентификация пользователя с выдачей JWT
     * @param loginUserDTO данные для аутентификации пользователя
     * @return данные для аутентификации пользователя посредством JWT
     */
    JwtAuthorizationDTO loginUser(LoginUserDTO loginUserDTO);

    UserEntity loadUserByUsername(String username) throws UsernameNotFoundException;
}
