package com.cashregister.userManagement.model.dto;


import com.cashregister.userManagement.model.ERole;
import com.cashregister.userManagement.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserDto {


    private String name;
    private String surname;
    private String username;

    private String mail;
    private String password;
    private Set<ERole> roles;


}
