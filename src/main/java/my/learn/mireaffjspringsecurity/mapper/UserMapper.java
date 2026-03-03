package my.learn.mireaffjspringsecurity.mapper;

import my.learn.mireaffjspringsecurity.dto.CreateUserDTO;
import my.learn.mireaffjspringsecurity.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    /**
     * Осуществляет маппинг сущности пользователя на DTO пользователя
     *
     * @param user сущность пользователя
     * @return DTO пользователя
     */
    @Mapping(target = "login", source = "login")
    @Mapping(target = "uuid", source = "uuid")
    @Mapping(target = "encryptedPass", source = "password")
    @Mapping(target = "roles", source = "role")
    UserDTO toUserDTO(UserEntity user);


    /**
     * Осуществляет маппинг входных данных для создания и регистрации пользователя
     * на сущность пользователя, uuid задается генеративно
     *
     * @param createUserDTO данные для создания и регистрации пользователя
     * @return сущность пользователя
     */
    @Mapping(target = "uuid", expression = "java(java.util.UUID.randomUUID().toString())")
    UserEntity toUserEntity(CreateUserDTO createUserDTO);
}
