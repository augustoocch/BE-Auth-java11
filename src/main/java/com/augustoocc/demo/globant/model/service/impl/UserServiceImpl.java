package com.augustoocc.demo.globant.model.service.impl;

import com.augustoocc.demo.globant.model.constants.RolesEnum;
import com.augustoocc.demo.globant.model.exceptions.GlobantException;
import com.augustoocc.demo.globant.model.repository.entity.User;
import com.augustoocc.demo.globant.model.repository.UserRepository;
import com.augustoocc.demo.globant.domain.user.dto.request.*;
import com.augustoocc.demo.globant.model.service.UserService;
import com.augustoocc.demo.globant.security.EncryptionConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;

import static com.augustoocc.demo.globant.model.constants.Constants.*;
import static com.augustoocc.demo.globant.model.constants.ErrorCode.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EncryptionConfig encryptionConfig;
    private final DateTimeFormatter dateTimeFormatter;

    public UserServiceImpl(UserRepository userRepository, EncryptionConfig encryptionConfig, DateTimeFormatter dateTimeFormatter) {
        this.userRepository = userRepository;
        this.encryptionConfig = encryptionConfig;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public LoginRequestDto login(EncodedRequest registerRequestDto) {
        return encryptionConfig
                .decryptObject(registerRequestDto.getPayload(), LoginRequestDto.class);
    }

    @Override
    public User loginExternal(EncodedRequest registerRequestDto) {
        LoginExternalDto userDto = encryptionConfig
                .decryptObject(registerRequestDto.getPayload(), LoginExternalDto.class);
        return this.findByEmail(userDto.getEmail());
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new GlobantException(USER_NOT_FOUND.getMessage(), USER_NOT_FOUND.getCode()));
    }

    @Override
    public User register(EncodedRequest registerReqEncoded) {
        RegisterRequestDto registerRequestDto = encryptionConfig
                .decryptObject(registerReqEncoded.getPayload(), RegisterRequestDto.class);
        log.info("Starting to register user with email ------ {} at {}", registerRequestDto.getEmail(), ZonedDateTime.now().format(dateTimeFormatter));
        validateUser(registerRequestDto);
        String encodedPassword = encryptionConfig.passwordEncrypt(registerRequestDto.getPassword());
        User newUser = new User(registerRequestDto.getName(), registerRequestDto.getSurname(), encodedPassword, registerRequestDto.getEmail());
        newUser.setRole(RolesEnum.ROLE_USER.getId());
        return userRepository.save(newUser);
    }

    @Override
    public User registerExternal(EncodedRequest registerReqEncoded) {
        RegisterRequestDto registerRequestDto = encryptionConfig
                .decryptObject(registerReqEncoded.getPayload(), RegisterRequestDto.class);
        log.info("Starting to register user with email ------ {} at {}", registerRequestDto.getEmail(), ZonedDateTime.now().format(dateTimeFormatter));
        String password = generateRandomPassword();
        registerRequestDto.setPassword(password);
        validateUser(registerRequestDto);
        String encodedPassword = encryptionConfig.passwordEncrypt(registerRequestDto.getPassword());
        User newUser = new User(registerRequestDto.getName(), registerRequestDto.getSurname(), encodedPassword, registerRequestDto.getEmail());
        newUser.setRole(RolesEnum.ROLE_USER.getId());
        return userRepository.save(newUser);
    }

    @Override
    public User updateUser(UpdateUserRequestDto user) {
        log.info("Starting to update user with email ------ {} at {}", user.getEmail(), ZonedDateTime.now().format(dateTimeFormatter));
        User userToUpdate = findByEmail(user.getEmail());
        if (!encryptionConfig.comparePassword(user.getPassword(), userToUpdate.getPassword())) {
            userToUpdate.setPassword(encryptionConfig.passwordEncrypt(user.getPassword()));
        }
        userToUpdate.setCity(user.getCity());
        userToUpdate.setCountry(user.getCountry());
        userToUpdate.setCelphone(user.getCelphone());
        userRepository.save(userToUpdate);
        return userToUpdate;
    }

    @Override
    public User updateUserPassword(EncodedRequest updatePasswEncoded) {
        UpdateUserPasswordDto user = encryptionConfig
                .decryptObject(updatePasswEncoded.getPayload(), UpdateUserPasswordDto.class);
        User userToUpdate = findByEmail(user.getEmail());
        if (encryptionConfig.comparePassword(user.getPassword(), userToUpdate.getPassword())) {
            userToUpdate.setPassword(encryptionConfig.passwordEncrypt(user.getPassword()));
            String newPasswd = encryptionConfig.passwordEncrypt(user.getNewPassword());
            userToUpdate.setPassword(newPasswd);
            userRepository.save(userToUpdate);
            log.info("User updated password successfully with email {} -- at {}", user.getEmail(), ZonedDateTime.now().format(dateTimeFormatter));
            return userToUpdate;
        } else {
            throw new GlobantException(INVALID_PASSWORD.getMessage(), INVALID_PASSWORD.getCode());
        }
    }

    @Override
    public void deleteUser(String email) {
        log.info("Starting to delete user with email ------ {} at {}", email, ZonedDateTime.now().format(dateTimeFormatter));
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
            throw new GlobantException(USER_ALREADY_EXISTS.getMessage(), USER_ALREADY_EXISTS.getCode());
        }
        return true;
    }

    public static String generateRandomPassword() {
        //Generates a password from 8 to 20 with at least one digit,
        //one lowercase, one uppercase. This applies only for external users
        final String lowerChars = LOWER_CHARS;
        final String upperChars = UPPER_CHARS;
        final String digits = NUMBERS;
        final String allChars = ALL_CHARACTERS;

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        int length = random.nextInt(12) + 8;
        password.append(lowerChars.charAt(random.nextInt(lowerChars.length())));
        password.append(upperChars.charAt(random.nextInt(upperChars.length())));
        password.append(digits.charAt(random.nextInt(digits.length())));

        for (int i = password.length(); i < length; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        char[] chars = password.toString().toCharArray();
        Collections.shuffle(Arrays.asList(chars));
        return new String(chars);
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
