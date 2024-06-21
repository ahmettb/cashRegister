package com.cashregister.authentacition.service;

import com.cashregister.authentacition.model.ERole;
import com.cashregister.authentacition.model.Role;
import com.cashregister.authentacition.model.User;
import com.cashregister.authentacition.model.UserInfo;
import com.cashregister.authentacition.model.request.LoginRequest;
import com.cashregister.authentacition.model.request.SignupRequest;
import com.cashregister.authentacition.repository.RoleRepository;
import com.cashregister.authentacition.repository.UserRepository;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
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


    public boolean validate(String token)
    {
        return jwtUtils.validateJwtToken(token);
    }

    public void registerUser(SignupRequest signupRequest) throws Exception {

        if (!userRepository.findByUsername(signupRequest.getUsername()).isEmpty()) {
            throw new Exception("Username already exist");
        }

        if (userRepository.existsByMail(signupRequest.getEmail())) {
            throw new Exception("Mail already exist");
        }


        User user = userRepository.findByUsername(signupRequest.getUsername()).orElse(null);

        if (user == null) {

            user = new User();
            user.setName(signupRequest.getName());
            user.setSurname(signupRequest.getSurname());
            user.setMail(signupRequest.getEmail());
            user.setUsername(signupRequest.getUsername());
            user.setPassword(encoder.encode(signupRequest.getPassword()));
            Role role = roleRepository.findByName(ERole.ROLE_ADMIN).orElse(null);
            if (role != null) {
                Set<Role> roles = new HashSet<>();
                roles.add(role);
                user.setRoles(roles);
            }


            userRepository.save(user);

        } else {
            throw new Exception("User already exist");
        }


    }


    public String login(LoginRequest loginRequest) throws Exception {
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElse(null);
        if (user == null) {
            throw new Exception("User not found");
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);


        String token = jwtUtils.generateJwtToken(authentication, user.getRoles());

        return token;

    }

    public UserInfo getUserInfo(String token)
    {
       String username= jwtUtils.getUserNameFromJwtToken(token);
      User user=  userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("User not found"));

      UserInfo userInfo=new UserInfo();
      userInfo.setId(user.getId());
      userInfo.setName(user.getName());
      userInfo.setSurname(user.getSurname());
      userInfo.setRole(userInfo.getRole());
      return  userInfo;
    }

    public UserInfo getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByUsername(username).orElse(null);
            if (user != null) {
                UserInfo userInfo = new UserInfo();
                userInfo.setId(user.getId());
                userInfo.setName(user.getName());
                userInfo.setSurname(user.getSurname());
                return userInfo;
            }
        }
        return null;
    }



}
