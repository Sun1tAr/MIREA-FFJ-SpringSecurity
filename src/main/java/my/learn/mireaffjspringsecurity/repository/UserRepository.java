package my.learn.mireaffjspringsecurity.repository;

import my.learn.mireaffjspringsecurity.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    /**
     * @param login логин пользователя
     * @return обертку над сущностью пользователя, необходимо проверять на empty()
     */
    @NativeQuery("SELECT * FROM {h-schema}user u WHERE u.login = ?1")
    Optional<UserEntity> findUserByLogin(String login);

    /**
     * @param login логин пользователя
     * @return true, если пользователь с таким логином есть, иначе false
     */
    boolean existsByLogin(String login);

    UserEntity findUserByUuid(String uuid);
}
