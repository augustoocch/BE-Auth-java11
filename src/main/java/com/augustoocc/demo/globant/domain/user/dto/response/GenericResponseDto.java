package com.augustoocc.demo.globant.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
public class GenericResponseDto {
    private String message;
    private String email;
    private ZonedDateTime timestamp;
}
