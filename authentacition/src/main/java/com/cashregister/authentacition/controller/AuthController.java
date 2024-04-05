package com.cashregister.authentacition.controller;

import com.cashregister.authentacition.model.ERole;
import com.cashregister.authentacition.model.Role;
import com.cashregister.authentacition.model.User;
import com.cashregister.authentacition.model.request.LoginRequest;
import com.cashregister.authentacition.model.request.SignupRequest;
import com.cashregister.authentacition.repository.RoleRepository;
import com.cashregister.authentacition.repository.UserRepository;
import com.cashregister.authentacition.service.UserDetailsImpl;
import jwt.JwtResponse;
import jwt.JwtUtils;
import jwt.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication, ERole.ROLE_ADMIN);
        // Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        // String username = loggedInUser.getName();

        //UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        System.out.println("deneme 322");

        // List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
        //,        .collect(Collectors.toList());

        return ResponseEntity.ok(new MessageResponse(jwt));

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {


        // Create new user's account
        User user = new User();
        user.setMail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setUsername(signUpRequest.getUsername());


        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}