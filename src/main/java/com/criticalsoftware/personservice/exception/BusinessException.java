package com.criticalsoftware.personservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String message;
    private String error;
    private HttpStatus httpStatusCode;
}
