package com.psl.exception;

import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@MicronautTest
public class CustomExceptionTest {
    @Test
    void testSetStatus() {
        String message="Test Customer Exception";
        HttpStatus httpStatus=HttpStatus.BAD_REQUEST;
        CustomException ex = new CustomException(message,null,httpStatus);
        ex.setHttpStatus(HttpStatus.OK);
        Assertions.assertEquals(HttpStatus.OK, ex.getHttpStatus());
    }

}
