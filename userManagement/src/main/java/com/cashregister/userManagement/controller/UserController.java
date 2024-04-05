package com.cashregister.userManagement.controller;


import com.cashregister.userManagement.model.dto.UserDto;
import com.cashregister.userManagement.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user-management")
@AllArgsConstructor
public class UserController {

    private final UserService userService;



    @PostMapping("addUser")
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

}
