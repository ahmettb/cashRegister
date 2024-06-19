package com.cashregister.userManagement.service;

import com.cashregister.userManagement.model.ERole;
import com.cashregister.userManagement.model.Role;
import com.cashregister.userManagement.model.User;
import com.cashregister.userManagement.model.dto.UserDto;
import com.cashregister.userManagement.repository.IUserRepository;
import com.cashregister.userManagement.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    private final IUserRepository userRepository;
    private final RoleRepository roleRepository;

    public void addUser(UserDto userDto)
    {


        User user=new User();
        user.setName(userDto.getName());

        user.setSurname(userDto.getSurname());
        user.setMail(userDto.getMail());
        user.setPassword(userDto.getPassword());

        Set<Role> rol=new HashSet<>();
        Role role=new Role();
        role.setName(ERole.ROLE_ADMIN);
        roleRepository.save(role);

        rol.add(role);
            user.setRoles(rol);

        userRepository.save(user);
    }

}
