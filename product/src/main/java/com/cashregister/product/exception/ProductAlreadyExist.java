
package com.cashregister.product.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)

public class ProductAlreadyExist extends  RuntimeException{

    public ProductAlreadyExist(String message)
    {
        super(message);
    }
}
