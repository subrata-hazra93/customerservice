package com.psl.exception;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

/**
 * Create CustomerExceptionHandler to handle CustomerException
 */
@Produces
@Singleton
@Requires(classes = {CustomException.class, ExceptionHandler.class})
public class CustomExceptionHandler implements ExceptionHandler<CustomException, HttpResponse<Error>> {
    @Override
    public HttpResponse<Error> handle(HttpRequest request, CustomException exception) {
        Error error=new Error();
        error.setMessage(exception.getMessage());
        error.setHttpStatus(exception.getHttpStatus());
        return HttpResponse.serverError(error).status(exception.getHttpStatus());
    }
}
