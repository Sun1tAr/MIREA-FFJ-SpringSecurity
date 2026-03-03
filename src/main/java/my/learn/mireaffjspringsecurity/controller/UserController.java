package my.learn.mireaffjspringsecurity.controller;

import lombok.RequiredArgsConstructor;
import my.learn.mireaffjspringsecurity.dto.CreateUserDTO;
import my.learn.mireaffjspringsecurity.dto.JwtAuthorizationDTO;
import my.learn.mireaffjspringsecurity.dto.LoginUserDTO;
import my.learn.mireaffjspringsecurity.dto.ResetPasswordDTO;
import my.learn.mireaffjspringsecurity.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<JwtAuthorizationDTO> registerUser(@RequestBody CreateUserDTO createUserDTO) {
        JwtAuthorizationDTO auth = authService.registerUser(createUserDTO);
        return new ResponseEntity<>(auth, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<JwtAuthorizationDTO> registerUser(@RequestBody LoginUserDTO loginUserDTO) {
        JwtAuthorizationDTO auth = authService.loginUser(loginUserDTO);
        return new ResponseEntity<>(auth, HttpStatus.OK);
    }


    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        authService.resetPassword(resetPasswordDTO);
        return ResponseEntity.noContent().build();
    }


}
