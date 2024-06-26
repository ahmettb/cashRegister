package com.cashregister.userManagement.service;

import com.cashregister.userManagement.exception.RoleNotFound;
import com.cashregister.userManagement.exception.UserNotFound;
import com.cashregister.userManagement.exception.UsernameAlreadyExist;
import com.cashregister.userManagement.model.ERole;
import com.cashregister.userManagement.model.Role;
import com.cashregister.userManagement.model.User;
import com.cashregister.userManagement.model.dto.RoleRequestDto;
import com.cashregister.userManagement.model.dto.RoleToUserDto;
import com.cashregister.userManagement.model.dto.UserDto;
import com.cashregister.userManagement.repository.IUserRepository;
import com.cashregister.userManagement.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Log4j2
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    public List<UserDto> getAllUsers() {
        log.info("Fetching all users");
        List<User> users = userRepository.getAllByDeletedFalse();
        List<UserDto> userDtos = new ArrayList<>();

        users.forEach(user -> {
            Set<ERole> roles = new HashSet<>();
            UserDto userDto = new UserDto();

            userDto.setName(user.getName());
            userDto.setSurname(user.getSurname());
            userDto.setUsername(user.getUsername());
            userDto.setMail(user.getMail());
            userDto.setPassword(user.getPassword());
            user.getRoles().forEach(role -> roles.add(role.getName()));
            userDto.setRoles(roles);
            userDtos.add(userDto);
        });

        log.info("Fetched {} users", userDtos.size());
        return userDtos;
    }

    public UserDto getUser(long userId) {
        log.info("Fetching user with ID {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with ID {}", userId);
                    return new UserNotFound("User not found");
                });

        UserDto userDto = new UserDto();
        Set<ERole> roles = new HashSet<>();
        userDto.setName(user.getName());
        userDto.setMail(user.getMail());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        user.getRoles().forEach(role -> roles.add(role.getName()));
        userDto.setRoles(roles);

        log.info("Fetched user with ID {}", userId);
        return userDto;
    }

    public void deleteUser(long userId) {
        log.info("Deleting user with ID {}", userId);
        User user = userRepository.getUserByIdAndDeletedFalse(userId)
                .orElseThrow(() -> {
                    log.error("User not found with ID {}", userId);
                    return new UserNotFound("User not found");
                });
        user.setDeleted(true);
        userRepository.save(user);
        log.info("User with ID {} marked as deleted", userId);
    }

    public User updateUser(long userId, UserDto userDto) {
        log.info("Updating user with ID {}", userId);
        User user = userRepository.findUserByIdAndDeletedFalse(userId)
                .orElseThrow(() -> {
                    log.error("User not found with ID {}", userId);
                    return new UserNotFound("User not found");
                });

        if (!user.getUsername().equals(userDto.getUsername()) &&
                userRepository.findByUsernameAndDeletedFalse(userDto.getUsername()).isPresent()) {
            log.error("Username {} already exists", userDto.getUsername());
            throw new UsernameAlreadyExist("Username already exists");
        }

        Set<Role> roles = new HashSet<>();
        userDto.getRoles().forEach(rol -> {
            Role role = roleRepository.findByName(rol)
                    .orElseThrow(() -> {
                        log.error("Role not found: {}", rol);
                        return new RoleNotFound(rol + " Not Found");
                    });
            roles.add(role);
        });

        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setMail(userDto.getMail());
        user.setUsername(userDto.getUsername());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setRoles(roles);
        user.setDeleted(false);

        userRepository.save(user);
        log.info("User with ID {} updated", userId);
        return user;
    }

    public User addUser(UserDto userDto) {
        log.info("Adding new user with username {}", userDto.getUsername());

        if (userRepository.findByUsernameAndDeletedFalse(userDto.getUsername()).isPresent()) {
            log.error("Username {} already exists", userDto.getUsername());
            throw new UsernameAlreadyExist("Username already exists");
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setMail(userDto.getMail());
        user.setUsername(userDto.getUsername());
        user.setPassword(encoder.encode(userDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        userDto.getRoles().forEach(rol -> {
            Role role = roleRepository.findByName(rol)
                    .orElseThrow(() -> {
                        log.error("Role not found: {}", rol);
                        return new RoleNotFound(rol + " Not Found");
                    });
            roles.add(role);
        });

        user.setRoles(roles);
        userRepository.save(user);
        log.info("User with username {} added", userDto.getUsername());
        return user;
    }

    public void addRoleToUser(RoleToUserDto roleToUserDto) {
        log.info("Adding roles to user with username {}", roleToUserDto.getUsername());
        User user = userRepository.findByUsername(roleToUserDto.getUsername())
                .orElseThrow(() -> {
                    log.error("User not found with username {}", roleToUserDto.getUsername());
                    return new UserNotFound("Error: User is not found.");
                });

        Set<Role> roles = new HashSet<>();
        roleToUserDto.getRoles().forEach(rol -> {
            Role role = roleRepository.findByName(rol)
                    .orElseThrow(() -> {
                        log.error("Role not found: {}", rol);
                        return new RoleNotFound("Error: Role is not found.");
                    });
            roles.add(role);
        });

        user.setRoles(roles);
        userRepository.save(user);
        log.info("Roles added to user with username {}", roleToUserDto.getUsername());
    }
}
