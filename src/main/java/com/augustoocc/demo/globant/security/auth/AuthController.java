package com.augustoocc.demo.globant.security.auth;

import com.augustoocc.demo.globant.domain.user.dto.request.LoginRequestDto;
import com.augustoocc.demo.globant.domain.user.dto.request.RegisterRequestDto;
import com.augustoocc.demo.globant.domain.user.dto.response.LoginResponseDto;
import com.augustoocc.demo.globant.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value="/auth/login", consumes="application/json", produces="application/json")
    public ResponseEntity<LoginResponseDto> login (@RequestBody LoginRequestDto loginRequestDto) {
        log.info("Login request received with email ------ {}", loginRequestDto.getEmail());
        LoginResponseDto responseDto = userService.login(loginRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping(value="/auth/logout", consumes="application/json", produces="application/json")
    public ResponseEntity<String> logout () {
        return ResponseEntity.ok("Logout successful");
    }

    @PostMapping(value="/auth/register", consumes="application/json", produces="application/json")
    public ResponseEntity<String> register (
            @RequestBody RegisterRequestDto registerRequestDto){
        userService.register(registerRequestDto);
        return ResponseEntity.ok("User registered successfully");
    }
}
