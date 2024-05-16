package com.augustoocc.demo.globant.security;

import com.augustoocc.demo.globant.domain.user.dto.request.LoginRequestDto;
import com.augustoocc.demo.globant.domain.user.dto.request.RegisterRequestDto;
import com.augustoocc.demo.globant.domain.user.dto.response.LoginResponseDto;
import com.augustoocc.demo.globant.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtService jwtProvider;
    private UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping(value="/auth/login", consumes="application/json", produces="application/json")
    public ResponseEntity<LoginResponseDto> login (@RequestBody LoginRequestDto loginRequestDto) {
        UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
                loginRequestDto.getEmail(), loginRequestDto.getPassword());

        Authentication authentication = authenticationManager.authenticate(userToken);
        String jwtToken = this.jwtProvider.generateToken(authentication);

        return ResponseEntity.ok(new LoginResponseDto(jwtToken));
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
