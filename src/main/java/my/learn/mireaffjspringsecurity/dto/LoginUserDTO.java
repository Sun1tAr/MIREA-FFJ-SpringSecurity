package my.learn.mireaffjspringsecurity.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor @NoArgsConstructor @Data
public class LoginUserDTO {

    private String login;

    private String password;

}
