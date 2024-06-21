package com.cashregister.authentacition.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {
    private long id;
    private String name;
    private String surname;
    private String role;
}
