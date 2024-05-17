package com.augustoocc.demo.globant.service.impl;

import com.augustoocc.demo.globant.domain.constants.RolesEnum;
import com.augustoocc.demo.globant.domain.exceptions.GlobantException;
import com.augustoocc.demo.globant.domain.model.User;
import com.augustoocc.demo.globant.domain.model.repository.UserRepository;
import com.augustoocc.demo.globant.domain.user.dto.request.LoginRequestDto;
import com.augustoocc.demo.globant.domain.user.dto.request.RegisterRequestDto;
import com.augustoocc.demo.globant.domain.user.dto.request.UpdateUserRequestDto;
import com.augustoocc.demo.globant.security.PasswordSecurity;
import com.augustoocc.demo.globant.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

import static com.augustoocc.demo.globant.domain.constants.Constants.EMAIL_VALID;
import static com.augustoocc.demo.globant.domain.constants.Constants.PASSWORD_VALID;
import static com.augustoocc.demo.globant.domain.constants.ErrorCode.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordSecurity passwordSecurity;

    public UserServiceImpl(UserRepository userRepository, PasswordSecurity passwordSecurity) {
        this.userRepository = userRepository;
        this.passwordSecurity = passwordSecurity;
    }

    @Override
    public User login(LoginRequestDto loginRequestDto) {
        log.info("Starting to search user with email ------ {} at {}", loginRequestDto.getEmail(), ZonedDateTime.now());
        return findByEmail(loginRequestDto.getEmail());
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new GlobantException(USER_NOT_FOUND.getMessage(), USER_NOT_FOUND.getCode()));
    }

    @Override
    public User register(RegisterRequestDto registerRequestDto) {
        log.info("Starting to register user with email ------ {} at {}", registerRequestDto.getEmail(), ZonedDateTime.now());
        validateUser(registerRequestDto);
        String encodedPassword = passwordSecurity.passowrdEncrypt(registerRequestDto.getPassword());
        User newUser = new User(registerRequestDto.getName(), registerRequestDto.getLastName(), encodedPassword, registerRequestDto.getEmail());
        newUser.setRole(RolesEnum.ROLE_USER.getId());
        return userRepository.save(newUser);
    }

    @Override
    public void updateUser(UpdateUserRequestDto user) {
        log.info("Starting to update user with email ------ {} at {}", user.getEmail(), ZonedDateTime.now());
        User userToUpdate = findByEmail(user.getEmail());
        if (!passwordSecurity.comparePassword(user.getPassword(), userToUpdate.getPassword())) {
            userToUpdate.setPassword(passwordSecurity.passowrdEncrypt(user.getPassword()));
        }
        userToUpdate.setCity(user.getCity());
        userToUpdate.setCountry(user.getCountry());
        userToUpdate.setCelphone(user.getCelphone());
        userRepository.save(userToUpdate);
    }

    @Override
    public void deleteUser(String email) {
        log.info("Starting to delete user with email ------ {} at {}", email, ZonedDateTime.now());
        User userToDelete = findByEmail(email);
        userRepository.delete(userToDelete);
    }

    private boolean validateUser(RegisterRequestDto user) {
        if (user.getEmail() == null || user.getPassword() == null) {
            throw new GlobantException(EMAIL_PASSWORD_REQUIRED.getMessage(), EMAIL_PASSWORD_REQUIRED.getCode());
        }
        if (!checkEmailFormat(user.getEmail())) {
            throw new GlobantException(INVALID_EMAIL_FORMAT.name(), INVALID_EMAIL_FORMAT.getCode());
        }
        if (!checkPasswordValidity(user.getPassword())) {
            throw new GlobantException(INVALID_PASSWORD.getMessage(), INVALID_PASSWORD.getCode());
        }
        if (existsByEmail(user.getEmail())) {
            throw new GlobantException(EMAIL_ALREADY_EXISTS.getMessage(), EMAIL_ALREADY_EXISTS.getCode());
        }
        return true;
    }

    private boolean checkPasswordValidity(String password) {
        //password from 8 to 20 with at least one digit, one lowercase, one uppercase
        return password.matches(PASSWORD_VALID);
    }

    private boolean checkEmailFormat(String email) {
        return email.matches(EMAIL_VALID);
    }

    private boolean existsByEmail(String email) {
        if( userRepository.findByEmail(email).isEmpty()){
            return false;
        };
        return true;
    }
}
