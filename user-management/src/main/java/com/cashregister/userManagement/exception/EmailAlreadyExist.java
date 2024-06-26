package com.cashregister.userManagement.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailAlreadyExist extends RuntimeException{

public EmailAlreadyExist(String message) {

    super(message);
}


}
