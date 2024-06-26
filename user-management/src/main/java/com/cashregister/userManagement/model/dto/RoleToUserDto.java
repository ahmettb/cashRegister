package com.cashregister.userManagement.model.dto;

import com.cashregister.userManagement.model.ERole;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RoleToUserDto {

String username;
Set<ERole> roles;


}
