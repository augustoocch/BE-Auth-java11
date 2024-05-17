package com.augustoocc.demo.globant.domain.exceptions;

public class GlobantException extends RuntimeException{

    private int code;

    public GlobantException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
