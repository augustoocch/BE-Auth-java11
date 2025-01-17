package com.augustoocc.demo.globant.model.exceptions;

import lombok.Getter;
import lombok.Setter;

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
