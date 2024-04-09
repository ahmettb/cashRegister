package com.cashregister.authentacition.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {

    private  String name;
    private  String surname;


    private  String username;
private String password;
private String email;
}
