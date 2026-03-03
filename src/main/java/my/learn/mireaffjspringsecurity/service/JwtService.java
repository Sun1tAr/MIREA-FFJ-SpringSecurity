package ru.ntdev.rpr.rpr_ms_rest.service;

import ru.ntdev.rpr.rpr_ms_rest.dto.internal.UserDTO;


public interface JwtService {

    /** Создает JWT токен на основе пользовательских данных
     * @param user DTO пользователя с учетными данными
     * @return сгенерированный JWT для данных указанного пользователя
     */
    String getJwtForUser(UserDTO user);

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
