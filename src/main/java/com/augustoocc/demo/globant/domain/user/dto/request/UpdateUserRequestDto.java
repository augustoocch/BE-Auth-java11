package com.augustoocc.demo.globant.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateUserRequestDto {
    private String token;
    private String password;
}
