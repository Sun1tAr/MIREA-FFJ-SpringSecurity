# Spring Security Learning Project

Учебный Java/Spring Boot-проект по аутентификации, авторизации и JWT.

## Стек

- Java;
- Spring Boot;
- Spring Security;
- JWT;
- PostgreSQL;
- Maven.

Все значения подключения и ключи задаются через переменные окружения.
Значения по умолчанию в конфигурации предназначены только для локальной
настройки.

## Запуск

Перед запуском задайте `POSTGRES_URL`, `POSTGRES_USER`, `POSTGRES_PASSWORD` и
`JWT_SECRET`. Затем выполните:

```bash
./mvnw test
./mvnw spring-boot:run
```

В Windows используйте `mvnw.cmd`. Приложение ожидает доступную PostgreSQL-базу;
схема создаётся приложением согласно настройкам JPA.
