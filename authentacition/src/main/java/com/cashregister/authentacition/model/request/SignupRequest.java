package com.cashregister.authentacition.model.request;

import com.cashregister.authentacition.model.ERole;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SignupRequest {

    private  String name;
    private  String surname;


    private  String username;
private String password;
private String email;

    private Set<ERole> roles;
}
