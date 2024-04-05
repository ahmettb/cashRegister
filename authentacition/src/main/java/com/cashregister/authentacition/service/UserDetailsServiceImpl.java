package com.cashregister.authentacition.service;

import com.cashregister.authentacition.model.ERole;
import com.cashregister.authentacition.model.User;
import com.cashregister.authentacition.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired

    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        SimpleGrantedAuthority userAuth=new SimpleGrantedAuthority(ERole.ROLE_ADMIN.toString());
        Collection<GrantedAuthority>authorities=new ArrayList<>();
        authorities.add(userAuth);

        return  new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
    }
}
