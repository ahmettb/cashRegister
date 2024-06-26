package com.cashregister.sale.model;


import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserInfo {

    private long id;
    private String name;
    private String surname;
    private List<String> role;
}
