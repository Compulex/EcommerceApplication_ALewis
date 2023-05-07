package com.ecommerce.EcommerceApplication.Exceptions;

import java.util.Date;

public class UnauthorizedUserException extends Exception{
    public UnauthorizedUserException(Date date, String eMessage){
        super(eMessage);
    }
}
