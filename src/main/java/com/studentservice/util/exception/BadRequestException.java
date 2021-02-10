package com.studentservice.util.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;

public class BadRequestException extends AppException {
    public BadRequestException(String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, message, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE));
    }
}