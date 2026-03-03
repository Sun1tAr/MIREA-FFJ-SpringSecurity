package my.learn.mireaffjspringsecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CreateUserDTO {

    private String login;

    private String password;

    private String fio;

    private String passport;

    private String inn;

    private String snils;

}
