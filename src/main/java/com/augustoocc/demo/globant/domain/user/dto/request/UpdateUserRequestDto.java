package com.augustoocc.demo.globant.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequestDto {
    private String email;
    private String password;
    private String city;
    private String country;
    private String celphone;
}
