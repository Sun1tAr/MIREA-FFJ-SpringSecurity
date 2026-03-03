package my.learn.mireaffjspringsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.learn.mireaffjspringsecurity.entity.RoleEntity;
import my.learn.mireaffjspringsecurity.entity.UserRole;
import my.learn.mireaffjspringsecurity.repository.RoleRepository;
import my.learn.mireaffjspringsecurity.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public RoleEntity findRoleByName(UserRole role) {
        Optional<RoleEntity> roleOpt = roleRepository.findRoleByRole(role);

        if (roleOpt.isEmpty()) {
            log.error("Попытка получения несуществующей роли: {}", role);
            throw new RuntimeException();
        }

        return roleOpt.get();
    }
}
