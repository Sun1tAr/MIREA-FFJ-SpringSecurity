package my.learn.mireaffjspringsecurity.mapper;

import my.learn.mireaffjspringsecurity.dto.CreateUserDTO;
import my.learn.mireaffjspringsecurity.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

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
