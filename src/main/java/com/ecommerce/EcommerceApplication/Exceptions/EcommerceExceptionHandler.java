package com.ecommerce.EcommerceApplication.Exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class EcommerceExceptionHandler {

    @ExceptionHandler(value = {UnauthorizedUserException.class})
    public ResponseEntity<Object> handlerUnauthorizedUserFoundException(UnauthorizedUserException uex, WebRequest webRequest){
        return new ResponseEntity<>(uex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {UserExistsException.class})
    public ResponseEntity<Object> handlerPasswordCredentialException(UserExistsException uex, WebRequest webRequest){
        return new ResponseEntity<>(uex.getMessage(), new HttpHeaders(), HttpStatus.ALREADY_REPORTED);
    }
}
