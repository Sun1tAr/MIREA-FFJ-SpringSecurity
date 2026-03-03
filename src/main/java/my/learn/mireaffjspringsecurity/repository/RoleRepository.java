package my.learn.mireaffjspringsecurity.repository;

import my.learn.mireaffjspringsecurity.entity.RoleEntity;
import my.learn.mireaffjspringsecurity.entity.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Integer> {

    Optional<RoleEntity> findRoleByRole(UserRole role);

}
