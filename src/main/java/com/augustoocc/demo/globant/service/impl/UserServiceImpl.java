package com.augustoocc.demo.globant.service.impl;

import com.augustoocc.demo.globant.domain.model.User;
import com.augustoocc.demo.globant.domain.user.dto.request.RegisterRequestDto;
import com.augustoocc.demo.globant.domain.user.dto.request.UpdateUserRequestDto;
import com.augustoocc.demo.globant.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public String login(String username, String password) {
        return null;
    }

    @Override
    public User loadUserByUsername(String username) {
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public User register(RegisterRequestDto user) {
        return null;
    }

    @Override
    public void updateUser(UpdateUserRequestDto user) {

    }
}
