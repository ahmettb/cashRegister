package com.cashregister.authentacition.controller;


import com.cashregister.authentacition.model.UserInfo;
import com.cashregister.authentacition.model.request.LoginRequest;
import com.cashregister.authentacition.model.request.SignupRequest;
import com.cashregister.authentacition.model.response.AuthentacitionResponse;
import com.cashregister.authentacition.repository.RoleRepository;
import com.cashregister.authentacition.repository.UserRepository;
import com.cashregister.authentacition.service.AuthService;

import jwt.JwtUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/auth")
@Log4j2
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

    @PostMapping("/login")
    public ResponseEntity<AuthentacitionResponse> login(@RequestBody LoginRequest loginRequest) throws Exception {

        log.info("AuthController : login method called for user", loginRequest.getUsername());
        return ResponseEntity.ok(authService.login(loginRequest));

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignupRequest signUpRequest) throws Exception {

        log.info("AuthController : register method entered");

        authService.registerUser(signUpRequest);
        return ResponseEntity.ok("Register success");
    }

    @GetMapping("/validate/{token}")
    public ResponseEntity<String> validate(@PathVariable("token")String token) throws Exception {

    boolean isValidate=authService.validate(token);

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