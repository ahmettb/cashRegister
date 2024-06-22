package com.cashregister.userManagement.controller;


import com.cashregister.userManagement.model.dto.RoleToUserDto;
import com.cashregister.userManagement.model.dto.UserDto;
import com.cashregister.userManagement.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user-management")
@AllArgsConstructor
public class UserController {

    @Autowired
    private  UserService userService;



    @PostMapping("save-user")
    public ResponseEntity<String>addUser(@RequestBody UserDto userDto)
    {

        try {
            userService.addUser(userDto);
            return new ResponseEntity<>("İşlem başarılı", HttpStatus.OK);
        }
        catch (Exception e)
        {
            throw  new RuntimeException("Hata oldu");
        }


    }


    @PostMapping("addRoleToUser")
    public ResponseEntity<?>addRoleToUser(@RequestBody RoleToUserDto roleToUserDto)
    {
        System.out.println("gsadsadsa");
        userService.addRoleToUser(roleToUserDto);
        return  new ResponseEntity("Role Added to"+roleToUserDto.getUsername(), HttpStatus.OK);


    }
    @GetMapping("getUser/{id}")
    public ResponseEntity<UserDto>addRoleToUser(@PathVariable("id")long id)
    {

        return  new ResponseEntity(userService.getUser(id), HttpStatus.OK);


    }
    @GetMapping("getAllUser")
    public ResponseEntity<List<UserDto>>getAllUser()
    {

        return  new ResponseEntity(userService.getAllUsers(), HttpStatus.OK);


    }


}
