package com.cashregister.authentacition.controller;


import com.cashregister.authentacition.model.UserInfo;
import com.cashregister.authentacition.model.request.LoginRequest;
import com.cashregister.authentacition.model.request.SignupRequest;
import com.cashregister.authentacition.repository.RoleRepository;
import com.cashregister.authentacition.repository.UserRepository;
import com.cashregister.authentacition.service.AuthService;

import io.micrometer.core.ipc.http.HttpSender;
import jwt.JwtUtils;
import jwt.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.util.HashSet;


@RestController
@RequestMapping("api/v6/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthService authService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginRequest loginRequest) throws Exception {


        return ResponseEntity.ok(authService.login(loginRequest));

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) throws Exception {

        authService.registerUser(signUpRequest);


        return ResponseEntity.ok("Register success");
    }

    @PostMapping("/validate/{token}")
    public ResponseEntity<String> validate(@PathVariable("token")String token) throws Exception {

    boolean isValidate=authService.validate(token);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return ResponseEntity.ok(isValidate ?"Is valid":"Not valid token");
    }

    @GetMapping("get-user-info")
    public ResponseEntity<UserInfo>getUserInfo(@RequestHeader("Authorization")String token) throws Exception
    {

       return new ResponseEntity<>( authService.getUserInfo(token),HttpStatus.OK);


    }

    @GetMapping("get-current-user")
    public ResponseEntity<UserInfo>getCurrentInfo()
    {

        return  new ResponseEntity<>(authService.getCurrentUser(),HttpStatus.OK);

    }


}