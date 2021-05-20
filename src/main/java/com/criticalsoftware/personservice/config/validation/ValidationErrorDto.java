package com.criticalsoftware.personservice.config.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidationErrorDto {
    private String message;
    private String field;
}
