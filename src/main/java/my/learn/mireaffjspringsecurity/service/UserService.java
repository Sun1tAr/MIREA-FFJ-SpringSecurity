package my.learn.mireaffjspringsecurity.service;

import my.learn.mireaffjspringsecurity.dto.CreateUserDTO;
import my.learn.mireaffjspringsecurity.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {

    UserEntity findUserByLogin(String login);

    boolean loginIsRegistered(String login);

    UserEntity createUser(CreateUserDTO createUserDTO);

}
