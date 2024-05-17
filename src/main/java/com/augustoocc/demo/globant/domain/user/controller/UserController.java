package com.augustoocc.demo.globant.domain.user.controller;

import com.augustoocc.demo.globant.domain.exceptions.ExceptionDto;
import com.augustoocc.demo.globant.domain.exceptions.GlobantException;
import com.augustoocc.demo.globant.domain.user.dto.request.DeleteUserRequestDto;
import com.augustoocc.demo.globant.domain.user.dto.request.UpdateUserRequestDto;
import com.augustoocc.demo.globant.domain.user.dto.response.GenericResponseDto;
import com.augustoocc.demo.globant.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RestController("/user")
public class UserController {

    private UserService userService;
    private DateTimeFormatter dateTimeFormatter;

    public UserController(UserService userService, DateTimeFormatter dateTimeFormatter) {
        this.userService = userService;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @PostMapping("/update/user")
    public ResponseEntity<GenericResponseDto> updateUser(@RequestBody UpdateUserRequestDto updateUserRequestDto) {
        userService.updateUser(updateUserRequestDto);
        return ResponseEntity.ok(new GenericResponseDto("User updated successfully", updateUserRequestDto.getEmail(), ZonedDateTime.now().format(dateTimeFormatter)));
    }

    @PostMapping("/delete/user")
    public ResponseEntity<GenericResponseDto> deleteUser(@RequestBody DeleteUserRequestDto deleteUserRequestDto) {
        userService.deleteUser(deleteUserRequestDto.getEmail());
        return ResponseEntity.ok(new GenericResponseDto("User deleted successfully", deleteUserRequestDto.getEmail(), ZonedDateTime.now().format(dateTimeFormatter)));
    }

    @ExceptionHandler(GlobantException.class)
    public ResponseEntity<ExceptionDto> handleGlobantException(GlobantException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(ZonedDateTime.now().format(dateTimeFormatter), ex.getCode());
        return new ResponseEntity<>(exceptionDto, HttpStatus.CONFLICT);
    }
}
