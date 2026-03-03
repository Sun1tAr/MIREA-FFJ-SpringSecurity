package my.learn.mireaffjspringsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.learn.mireaffjspringsecurity.dto.CreateUserDTO;
import my.learn.mireaffjspringsecurity.entity.UserEntity;
import my.learn.mireaffjspringsecurity.entity.UserRole;
import my.learn.mireaffjspringsecurity.mapper.UserMapper;
import my.learn.mireaffjspringsecurity.repository.UserRepository;
import my.learn.mireaffjspringsecurity.service.RoleService;
import my.learn.mireaffjspringsecurity.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserMapper userMapper;

    @Override
    public UserEntity findUserByLogin(String login) {
        Optional<UserEntity> userOpt = userRepository.findUserByLogin(login);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("NotFoundAppException");
        }
        return userOpt.get();
    }

    @Override
    public boolean loginIsRegistered(String login) {
        return userRepository.existsByLogin(login);
    }

    @Override
    @Transactional
    public UserEntity createUser(CreateUserDTO createUserDTO) {
        UserEntity user = userMapper.toUserEntity(createUserDTO);
        user.setRole(List.of(roleService.findRoleByName(UserRole.USER)));

        UserEntity saved;
        try {
            saved = userRepository.save(user);
        } catch (Exception e) {
            log.error("Не получилось создать пользователя {}: {}", createUserDTO, e.getMessage());
            throw new RuntimeException("InternalServerErrorAppException");
        }

        return saved;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (loginIsRegistered(username)) {
            return findUserByLogin(username);
        } else {
            throw UsernameNotFoundException.fromUsername(username);
        }
    }

}
