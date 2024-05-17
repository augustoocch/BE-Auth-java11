package com.augustoocc.demo.globant.security.auth;

import com.augustoocc.demo.globant.domain.model.User;
import com.augustoocc.demo.globant.domain.user.dto.request.LoginRequestDto;
import com.augustoocc.demo.globant.domain.user.dto.request.RegisterRequestDto;
import com.augustoocc.demo.globant.domain.user.dto.response.LoginResponseDto;
import com.augustoocc.demo.globant.domain.user.dto.response.GenericResponseDto;
import com.augustoocc.demo.globant.security.JwtService;
import com.augustoocc.demo.globant.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@Slf4j
@RestController
public class AuthController {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtService jwtProvider;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtProvider ) {
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping(value="/auth/login",
            consumes=MediaType.APPLICATION_JSON_VALUE,
            produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponseDto> login (@RequestBody LoginRequestDto loginRequestDto) {
        log.info("Login request received with email ------ {}", loginRequestDto.getEmail());
        userService.login(loginRequestDto);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
        );
        String jwtToken = jwtProvider.generateToken(authentication);
        LoginResponseDto response = new LoginResponseDto(jwtToken);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value="/auth/logout",
            consumes=MediaType.APPLICATION_JSON_VALUE,
            produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> logout () {
        return ResponseEntity.ok("Logout successful");
    }

    @PostMapping(value="/auth/register",
            consumes=MediaType.APPLICATION_JSON_VALUE,
            produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponseDto> register (
            @RequestBody RegisterRequestDto registerRequestDto){
        userService.register(registerRequestDto);
        return ResponseEntity.ok(new GenericResponseDto("User registered successfully",
                registerRequestDto.getEmail(), ZonedDateTime.now()));
    }
}
