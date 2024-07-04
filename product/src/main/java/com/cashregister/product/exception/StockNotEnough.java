package com.cashregister.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)

public class StockNotEnough extends RuntimeException{

    public StockNotEnough(String msg) {

        super(msg);
    }
}
