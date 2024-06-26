package com.cashregister.authentacition.model;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserInfo {
    private long id;
    private String name;
    private String surname;
    private List<String> role;

}
