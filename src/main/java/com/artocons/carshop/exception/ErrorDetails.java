package com.artocons.carshop.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDetails {
    private HttpStatus statusCode;
    private String message;
    private String details;
}
