package my.learn.mireaffjspringsecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JwtAuthorizationDTO {

    private final String accessToken;


}
