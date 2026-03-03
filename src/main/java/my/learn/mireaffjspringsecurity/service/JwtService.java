package my.learn.mireaffjspringsecurity.service;

import my.learn.mireaffjspringsecurity.entity.UserEntity;

public interface JwtService {

    /** Создает JWT токен на основе пользовательских данных
     * @param user DTO пользователя с учетными данными
     * @return сгенерированный JWT для данных указанного пользователя
     */
    String getJwtForUser(UserEntity user);

    /** Проверяет валидность JWT токена (подпись, клеймы)
     * @param token JWT
     * @return true если токен валидный, иначе false
     */
    boolean tokenIsValid(String token);

    /** разбирает JWT и получает из него логин пользователя
     * @param token JWT токен
     * @return логин пользователя, указанный в JWT
     */
    String getLoginFromJwt(String token);

    /**
     * @param token JWT токен
     * @return uuid пользователя, объявленный в клеймах токена
     */
    String getUuidFromJwt(String token);


}
