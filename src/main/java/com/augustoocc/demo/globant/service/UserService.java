package com.augustoocc.demo.globant.service;

import com.augustoocc.demo.globant.domain.model.User;
import com.augustoocc.demo.globant.domain.user.dto.request.LoginRequestDto;
import com.augustoocc.demo.globant.domain.user.dto.request.RegisterRequestDto;
import com.augustoocc.demo.globant.domain.user.dto.request.UpdateUserRequestDto;
import com.augustoocc.demo.globant.domain.user.dto.response.LoginResponseDto;

public interface UserService {
    LoginResponseDto login(LoginRequestDto loginRequestDto);
    User loadUserByUsername(String username);
    User findByEmail(String email);
    User register(RegisterRequestDto user);
    void updateUser(UpdateUserRequestDto user);
}
