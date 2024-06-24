package com.cashregister.authentacition.service;

import com.cashregister.authentacition.exception.EmailAlreadyExist;
import com.cashregister.authentacition.exception.RoleNotFound;
import com.cashregister.authentacition.exception.UserNotFound;
import com.cashregister.authentacition.exception.UsernameAlreadyExist;
import com.cashregister.authentacition.model.ERole;
import com.cashregister.authentacition.model.Role;
import com.cashregister.authentacition.model.User;
import com.cashregister.authentacition.model.UserInfo;
import com.cashregister.authentacition.model.request.LoginRequest;
import com.cashregister.authentacition.model.request.SignupRequest;
import com.cashregister.authentacition.model.response.AuthentacitionResponse;
import com.cashregister.authentacition.repository.RoleRepository;
import com.cashregister.authentacition.repository.UserRepository;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jwt.JwtUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

@Service
@Log4j2
public class AuthService {


    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;


    public boolean validate(String token) {
        log.info("Validating token: {}", token);
        return jwtUtils.validateJwtToken(token);
    }

    public void registerUser(SignupRequest signupRequest) throws Exception {
        log.info("Registering user with email: {}", signupRequest.getEmail());

        if (userRepository.existsByMail(signupRequest.getEmail())) {
            log.error("Email already exists: {}", signupRequest.getEmail());
            throw new EmailAlreadyExist("Mail already exist");
        }

        userRepository.findByUsername(signupRequest.getUsername()).ifPresent(user -> {
            log.error("Username already exists: {}", signupRequest.getUsername());
            throw new UsernameAlreadyExist("Username already exist");
        });

        User user = new User();
        user.setName(signupRequest.getName());
        user.setSurname(signupRequest.getSurname());
        user.setMail(signupRequest.getEmail());
        user.setUsername(signupRequest.getUsername());
        user.setPassword(encoder.encode(signupRequest.getPassword()));
        Set<Role> roleSet = new HashSet<>();

        signupRequest.getRoles().forEach(rol -> {
            Role role = roleRepository.findByName(rol).orElseThrow(() -> {
                log.error("Role not found: {}", rol);
                return new RoleNotFound("Role not found");
            });
            roleSet.add(role);
        });

        user.setRoles(roleSet);

        userRepository.save(user);
        log.info("User registered successfully: {}", signupRequest.getUsername());
    }


    public AuthentacitionResponse login(LoginRequest loginRequest) throws Exception {
        log.info("Logging in user: {}", loginRequest.getUsername());

        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> {
            log.error("User not found: {}", loginRequest.getUsername());
            return new UserNotFound("User Not Found");
        });

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtils.generateJwtToken(authentication, user.getRoles());

        AuthentacitionResponse authentacitionResponse = new AuthentacitionResponse();
        authentacitionResponse.setUsername(loginRequest.getUsername());
        authentacitionResponse.setAccesToken(token);

        log.info("User logged in successfully: {}", loginRequest.getUsername());

        return authentacitionResponse;
    }

    public UserInfo getUserInfo(String token) {
        log.info("Getting user info from token: {}", token);
        String username = jwtUtils.getUserNameFromJwtToken(token);
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            log.error("User not found for token: {}", token);
            return new RuntimeException("User not found");
        });

        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setName(user.getName());
        userInfo.setSurname(user.getSurname());
        userInfo.setRole(userInfo.getRole());
        log.info("User info retrieved successfully for user: {}", username);
        return userInfo;
    }

    public UserInfo getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            log.info("Getting current user info for username: {}", username);
            User user = userRepository.findByUsername(username).orElse(null);
            if (user != null) {
                UserInfo userInfo = new UserInfo();
                userInfo.setId(user.getId());
                userInfo.setName(user.getName());
                userInfo.setSurname(user.getSurname());
                log.info("Current user info retrieved successfully for user: {}", username);
                return userInfo;
            }
        }
        log.warn("No authenticated user found");
        return null;
    }
}
