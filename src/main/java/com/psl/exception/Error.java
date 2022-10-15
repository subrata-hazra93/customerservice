package com.psl.exception;

import io.micronaut.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Error bean for customer exception
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Error {
    private HttpStatus httpStatus;
    private String message;
}
