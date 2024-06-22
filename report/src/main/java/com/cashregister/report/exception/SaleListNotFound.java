package com.cashregister.report.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SaleListNotFound extends RuntimeException{

        private ExceptionMessage exceptionMessage;


    public SaleListNotFound(String message, ExceptionMessage exceptionMessage) {
        super(message);
        this.exceptionMessage = exceptionMessage;
    }
    public SaleListNotFound(String message) {
        super(message);


    }
    public SaleListNotFound(ExceptionMessage exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public ExceptionMessage getExceptionMessage() {


     return exceptionMessage;
    }
}
