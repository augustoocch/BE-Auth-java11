package com.augustoocc.demo.globant.service.impl;

import com.augustoocc.demo.globant.domain.constants.RolesEnum;
import com.augustoocc.demo.globant.domain.model.Roles;
import com.augustoocc.demo.globant.domain.model.User;
import com.augustoocc.demo.globant.domain.model.repository.UserRepository;
import com.augustoocc.demo.globant.domain.user.dto.request.LoginRequestDto;
import com.augustoocc.demo.globant.domain.user.dto.request.RegisterRequestDto;
import com.augustoocc.demo.globant.domain.user.dto.request.UpdateUserRequestDto;
import com.augustoocc.demo.globant.domain.user.dto.response.LoginResponseDto;
import com.augustoocc.demo.globant.security.JwtService;
import com.augustoocc.demo.globant.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private AuthenticationManager authenticationManager;
    private JwtService jwtProvider;
    private UserRepository userRepository;

    public UserServiceImpl(JwtService jwtProvider, UserRepository userRepository) {
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        log.info("Starting to search user with email ------ {}", loginRequestDto.getEmail());
        User user = findByEmail(loginRequestDto.getEmail());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
        );
        String jwtToken = jwtProvider.generateToken(authentication);

        return new LoginResponseDto(jwtToken);
    }

    @Override
    public User loadUserByUsername(String username) {
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User register(RegisterRequestDto user) {
        validateUser(user);
        User newUser = new User(user.getName(), user.getLastName(),user.getPassword(), user.getEmail());
        newUser.setRole(RolesEnum.ROLE_USER.getId());
        return userRepository.save(newUser);
    }

    @Override
    public void updateUser(UpdateUserRequestDto user) {

    }

    private boolean validateUser(RegisterRequestDto user) {
        if (user.getEmail() == null || user.getPassword() == null) {
            throw new RuntimeException("Email and password are required");
        }
        if (!checkEmailFormat(user.getEmail())) {
            throw new RuntimeException("Email format is not valid");
        }
        if (!checkPasswordValidity(user.getPassword())) {
            throw new RuntimeException("Password must have at least 8 characters, one digit, one lowercase and one uppercase letter");
        }
        if (existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        return true;
    }

    private boolean checkPasswordValidity(String password) {
        //password from 8 to 20 with at least one digit, one lowercase, one uppercase
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$");
    }

    private boolean checkEmailFormat(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

    private boolean existsByEmail(String email) {
        if( userRepository.findByEmail(email).isEmpty()){
            return false;
        };
        return true;
    }
}
