package com.cashregister.userManagement.controller;

import com.cashregister.userManagement.model.User;
import com.cashregister.userManagement.model.dto.RoleToUserDto;
import com.cashregister.userManagement.model.dto.UserDto;
import com.cashregister.userManagement.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user-management")
@AllArgsConstructor
@Log4j2
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("update-user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody UserDto userDto) {
        log.info("UserController: updateUser method called with ID {}", id);
        User updatedUser = userService.updateUser(id, userDto);
        log.info("UserController: updateUser method completed for ID {}", id);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PostMapping("save-user")
    public ResponseEntity<String> addUser(@RequestBody UserDto userDto) {
        log.info("UserController: addUser method called");
        userService.addUser(userDto);
        log.info("UserController: addUser method completed");
        return new ResponseEntity<>("User added", HttpStatus.OK);
    }

    @PostMapping("addRoleToUser")
    public ResponseEntity<String> addRoleToUser(@RequestBody RoleToUserDto roleToUserDto) {
        log.info("UserController: addRoleToUser method called for username {}", roleToUserDto.getUsername());
        userService.addRoleToUser(roleToUserDto);
        log.info("UserController: addRoleToUser method completed for username {}", roleToUserDto.getUsername());
        return new ResponseEntity<>("Role added to " + roleToUserDto.getUsername(), HttpStatus.OK);
    }

    @GetMapping("getUser/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") long id) {
        log.info("UserController: getUser method called with ID {}", id);
        UserDto userDto = userService.getUser(id);
        log.info("UserController: getUser method completed for ID {}", id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("getAllUser")
    public ResponseEntity<List<UserDto>> getAllUser() {
        log.info("UserController: getAllUser method called");
        List<UserDto> userDtos = userService.getAllUsers();
        log.info("UserController: getAllUser method completed with {} users fetched", userDtos.size());
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }
}
