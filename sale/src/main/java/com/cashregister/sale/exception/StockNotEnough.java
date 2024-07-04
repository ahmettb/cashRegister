package com.cashregister.sale.exception;

import com.cashregister.sale.error.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class StockNotEnough extends RuntimeException{
    ExceptionMessage exceptionMessage;

    public StockNotEnough(String msg) {

        super(msg);
    }

    public StockNotEnough(ExceptionMessage exceptionMessage) {

        this.exceptionMessage = exceptionMessage;
    }

    public ExceptionMessage getExceptionMessage()
    {
        return exceptionMessage;
    }
}
