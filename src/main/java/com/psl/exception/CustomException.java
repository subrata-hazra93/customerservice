package com.psl.exception;

import io.micronaut.http.HttpStatus;
import java.io.Serializable;

/**
 * Create Customer Exception generic class for all type of exceptions
 *
 */

public class CustomException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 1L;
    private  HttpStatus httpStatus;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }


    public CustomException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus=httpStatus;
    }

    public CustomException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
        this.httpStatus=httpStatus;
    }

    public CustomException(Throwable cause) {
        super(cause);
    }


}
