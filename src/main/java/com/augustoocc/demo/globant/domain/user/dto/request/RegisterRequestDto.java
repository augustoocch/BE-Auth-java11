package com.augustoocc.demo.globant.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterRequestDto {
    private String email;
    private String password;
    private String name;
    private String lastName;
}
