package com.augustoocc.demo.globant.model.service;

import com.augustoocc.demo.globant.model.repository.entity.User;
import com.augustoocc.demo.globant.domain.user.dto.request.LoginRequestDto;
import com.augustoocc.demo.globant.domain.user.dto.request.EncodedRequest;
import com.augustoocc.demo.globant.domain.user.dto.request.UpdateUserRequestDto;

public interface UserService {
    LoginRequestDto login(EncodedRequest registerRequestDto);
    User loginExternal(EncodedRequest registerRequestDto);
    User findByEmail(String email);
    User register(EncodedRequest registerReqEncoded);
    User registerExternal(EncodedRequest registerReqEncoded);
    User updateUser(UpdateUserRequestDto user);
    User updateUserPassword(EncodedRequest updatePasswEncoded);
    void deleteUser(String email);
}
