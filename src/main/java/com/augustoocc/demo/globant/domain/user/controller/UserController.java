package com.augustoocc.demo.globant.domain.user.controller;

import com.augustoocc.demo.globant.domain.user.dto.request.UpdateUserRequestDto;
import com.augustoocc.demo.globant.security.JwtService;
import com.augustoocc.demo.globant.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
public class UserController {

    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private UserService userService;

    @PostMapping("/update/user")
    public void updateUser(@RequestBody UpdateUserRequestDto updateUserRequestDto) {
        //Authenticate before letting pass the request
        jwtService.validateToken(updateUserRequestDto.getToken());
        userService.updateUser(updateUserRequestDto);
    }

    @PostMapping("/delete/user")
    public void deleteUser() {

    }
}
