package com.augustoocc.demo.globant.service;

import com.augustoocc.demo.globant.domain.model.User;
import com.augustoocc.demo.globant.domain.user.dto.request.RegisterRequestDto;
import com.augustoocc.demo.globant.domain.user.dto.request.UpdateUserRequestDto;

public interface UserService {
    String login(String username, String password);
    User loadUserByUsername(String username);
    User findByEmail(String email);
    User register(RegisterRequestDto user);
    void updateUser(UpdateUserRequestDto user);
}
