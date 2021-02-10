package com.studentservice.util.exception;

import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final ErrorAttributes errorAttributes;

    @ExceptionHandler(AppException.class)
    public ResponseEntity<Map<String, Object>> appHandler(AppException ex, WebRequest request) {
        Map<String, Object> body = this.errorAttributes.getErrorAttributes(request, ex.getOptions());
        HttpStatus status = ex.getStatus();
        body.put("status", status);
        body.put("error", status.getReasonPhrase());
        return ResponseEntity.status(status).body(body);
    }
}
