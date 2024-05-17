package com.augustoocc.demo.globant.domain.user.controller;

import com.augustoocc.demo.globant.domain.user.dto.request.DeleteUserRequestDto;
import com.augustoocc.demo.globant.domain.user.dto.request.UpdateUserRequestDto;
import com.augustoocc.demo.globant.domain.user.dto.response.GenericResponseDto;
import com.augustoocc.demo.globant.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@RestController("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/update/user")
    public ResponseEntity<GenericResponseDto> updateUser(@RequestBody UpdateUserRequestDto updateUserRequestDto) {
        userService.updateUser(updateUserRequestDto);
        return ResponseEntity.ok(new GenericResponseDto("User updated successfully", updateUserRequestDto.getEmail(), ZonedDateTime.now()));
    }

    @PostMapping("/delete/user")
    public ResponseEntity<GenericResponseDto> deleteUser(@RequestBody DeleteUserRequestDto deleteUserRequestDto) {
        userService.deleteUser(deleteUserRequestDto.getEmail());
        return ResponseEntity.ok(new GenericResponseDto("User deleted successfully", deleteUserRequestDto.getEmail(), ZonedDateTime.now()));
    }
}
