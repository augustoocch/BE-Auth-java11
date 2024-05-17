package com.augustoocc.demo.globant.service;

import com.augustoocc.demo.globant.domain.model.User;
import com.augustoocc.demo.globant.domain.user.dto.request.LoginRequestDto;
import com.augustoocc.demo.globant.domain.user.dto.request.RegisterRequestDto;
import com.augustoocc.demo.globant.domain.user.dto.request.UpdateUserRequestDto;

public interface UserService {
    User login(LoginRequestDto loginRequestDto);
    User findByEmail(String email);
    User register(RegisterRequestDto user);
    void updateUser(UpdateUserRequestDto user);
    void deleteUser(String email);
}
