package com.cashregister.userManagement.service;

import com.cashregister.userManagement.model.ERole;
import com.cashregister.userManagement.model.Role;
import com.cashregister.userManagement.model.User;
import com.cashregister.userManagement.model.dto.RoleRequestDto;
import com.cashregister.userManagement.model.dto.RoleToUserDto;
import com.cashregister.userManagement.model.dto.UserDto;
import com.cashregister.userManagement.repository.IUserRepository;
import com.cashregister.userManagement.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

@Autowired
   private  PasswordEncoder encoder;



    public List<UserDto> getAllUsers() {

        List<User>users = userRepository.getAllByDeletedFalse();
        List<UserDto>userDtos = new ArrayList<UserDto>();

        users.forEach(user -> {
            Set<ERole>roles=new HashSet<>();

            UserDto userDto=new UserDto();

            userDto.setName(user.getName());
            userDto.setSurname(user.getSurname());
            userDto.setUsername(user.getUsername());
            userDto.setMail(user.getMail());
            userDto.setPassword(user.getPassword());
            user.getRoles().forEach(role -> {
                roles.add(role.getName());

            });
            userDto.setRoles(roles);
            userDtos.add(userDto);


        });

        return userDtos;
    }

    public UserDto getUser(long userId) {

       User user= userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
       UserDto userDto = new UserDto();
       Set<ERole> roles = new HashSet<>();
       userDto.setName(user.getName());
        userDto.setMail(user.getMail());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(encoder.encode( user.getPassword()));
        user.getRoles().forEach(role -> {
            roles.add(role.getName());


        });
userDto.setRoles(roles);

return userDto;

    }

    public void deleteUser(long userId) {

        User user=userRepository.getUserByIdAndDeletedFalse(userId).orElseThrow(()->new RuntimeException("User not found"));
        user.setDeleted(true);

        userRepository.save(user);

    }

    public void updateUser(long userId, UserDto userDto) {

        User user=userRepository.findUserByIdAndDeletedFalse(userId).orElseThrow(()->new RuntimeException("User not found"));
        if(userRepository.findByUsernameAndDeletedFalse(userDto.getUsername()).isPresent())
        {
            throw new RuntimeException("Username already exists");
        }
        user.setName(userDto.getName());
        user.setMail(userDto.getMail());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setDeleted(false);

        userRepository.save(user);




    }


    public void addUser(UserDto userDto) {

        if(userRepository.findByUsernameAndDeletedFalse(userDto.getUsername()).isPresent())
        {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setMail(userDto.getMail());
        user.setPassword(encoder.encode(userDto.getPassword()));

        Set<Role> roles = new HashSet<>();


        for (ERole eRole : userDto.getRoles()) {

            Role role = roleRepository.findByName(eRole)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(role);
        }

        user.setRoles(roles);
        userRepository.save(user);
    }


    public void addRoleToUser(RoleToUserDto roleToUserDto) {
        User user = userRepository.findByUsername(roleToUserDto.getUsername()).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Set<Role> roles = new HashSet<>();

        if (user.getRoles().isEmpty()) {
            for (ERole eRole : roleToUserDto.getRoles()) {

                Role role = roleRepository.findByName(eRole)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(role);
                }

        } else {
            user.getRoles().forEach(role1 -> {

                for (ERole eRole : roleToUserDto.getRoles()) {

                    Role role = roleRepository.findByName(eRole)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    if (!role.equals(role1)) {
                        roles.add(role);
                        roles.add(role1);
                    }

                }


            });
            user.setRoles(roles);
            userRepository.save(user);

        }
    }
}


