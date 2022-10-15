package com.psl.exception;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@MicronautTest
 class CustomExceptionHandlerTest {
    @Inject
    CustomExceptionHandler customExceptionHandler;
    @Test
    void testCustomExceptionHandler(){
        CustomException customException=new CustomException("Testing Custom Exception", HttpStatus.BAD_REQUEST);

        HttpResponse<Error> error= customExceptionHandler.handle(null,customException);
        System.out.println(HttpStatus.BAD_REQUEST);
        System.out.println(error.getStatus());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, error.getStatus());

    }


}
