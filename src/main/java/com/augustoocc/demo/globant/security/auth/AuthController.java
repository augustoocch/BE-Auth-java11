package com.augustoocc.demo.globant.security.auth;

import com.augustoocc.demo.globant.model.exceptions.ExceptionDto;
import com.augustoocc.demo.globant.model.exceptions.GlobantException;
import com.augustoocc.demo.globant.model.repository.entity.User;
import com.augustoocc.demo.globant.domain.user.dto.request.LoginRequestDto;
import com.augustoocc.demo.globant.domain.user.dto.request.EncodedRequest;
import com.augustoocc.demo.globant.domain.user.dto.response.LoginResponseDto;
import com.augustoocc.demo.globant.domain.user.dto.response.GenericResponseDto;
import com.augustoocc.demo.globant.security.JwtService;
import com.augustoocc.demo.globant.model.service.UserService;
import com.augustoocc.demo.globant.model.service.impl.UserDetailedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
public class AuthController {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtService jwtProvider;
    private DateTimeFormatter dateTimeFormatter;
    private UserDetailedService userDetailedService;

    public AuthController(UserService userService, AuthenticationManager authenticationManager,
                          JwtService jwtProvider, DateTimeFormatter dateTimeFormatter,
                          UserDetailedService userDetailedService) {
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.dateTimeFormatter = dateTimeFormatter;
        this.userDetailedService = userDetailedService;
    }

    @PostMapping(value="/auth/login",
            consumes=MediaType.APPLICATION_JSON_VALUE,
            produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponseDto> login (@RequestBody EncodedRequest loginRequest) {
           LoginRequestDto loginRequestDto= userService.login(loginRequest);
           Authentication authentication = authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
           );
           String jwtToken = jwtProvider.generateToken(authentication);
           LoginResponseDto response = new LoginResponseDto(jwtToken);
           log.info("User {} logged in successfully at ---- {}", loginRequestDto.getEmail(), ZonedDateTime.now().format(dateTimeFormatter));
           return ResponseEntity.ok(response);
    }

    @PostMapping(value="/auth/login/external",
            consumes=MediaType.APPLICATION_JSON_VALUE,
            produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponseDto> loginExternal (@RequestBody EncodedRequest loginRequest) {
            User user = userService.loginExternal(loginRequest);
            UserDetails userDetails = userDetailedService.loadUserByUsername(user.getEmail());
            String jwtToken = jwtProvider.generateToken(userDetails);
            LoginResponseDto response = new LoginResponseDto(jwtToken);
            log.info("User {} logged in successfully with External account at ---- {}", user.getEmail(), ZonedDateTime.now().format(dateTimeFormatter));
            return ResponseEntity.ok(response);
    }

    @PostMapping(value="/auth/register",
            consumes=MediaType.APPLICATION_JSON_VALUE,
            produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponseDto> register (
            @RequestBody EncodedRequest registerRequestDto) throws Exception {
        User usr = userService.register(registerRequestDto);
        return ResponseEntity.ok(new GenericResponseDto("User registered successfully",
                usr.getEmail(), ZonedDateTime.now().format(dateTimeFormatter)));
    }

    @PostMapping(value="/auth/register/external",
            consumes=MediaType.APPLICATION_JSON_VALUE,
            produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponseDto> registerExternal (
            @RequestBody EncodedRequest registerRequestDto) throws Exception {
        User usr = userService.registerExternal(registerRequestDto);
        return ResponseEntity.ok(new GenericResponseDto("User registered successfully",
                usr.getEmail(), ZonedDateTime.now().format(dateTimeFormatter)));
    }

    @ExceptionHandler(GlobantException.class)
    public ResponseEntity<ExceptionDto> handleGlobantException(GlobantException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(ZonedDateTime.now().format(dateTimeFormatter), ex.getCode());
        return new ResponseEntity<>(exceptionDto, HttpStatus.CONFLICT);
    }
}
