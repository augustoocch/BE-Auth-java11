package com.augustoocc.demo.globant.domain.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class ExceptionDto {

        private String dateTime;
        private int code;

        public ExceptionDto(String message, int code) {
            this.dateTime = message;
            this.code = code;
        }
}
