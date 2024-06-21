package com.cashregister.sale.exception;

import com.cashregister.sale.error.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)

    public class NotFoundProduct extends  RuntimeException{

    private ExceptionMessage  exceptionMessage;

    public NotFoundProduct(String   message)
    {
        super(message);
    }
    public NotFoundProduct(ExceptionMessage  message)
    {
        this.exceptionMessage=message;
    }
    public NotFoundProduct(String message,ExceptionMessage exceptionMessage)
    {
        super(message);
        this.exceptionMessage=exceptionMessage;
    }


    public ExceptionMessage getExceptionMessage()
    {
        return  exceptionMessage;
    }
}
