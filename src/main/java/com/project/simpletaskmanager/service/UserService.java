package com.project.simpletaskmanager.service;

import com.project.simpletaskmanager.entity.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {

    User registerUser(User user);
    User findById(Long id);

    User findByEmail(String email);

    String verify(User user, AuthenticationManager authenticationManager);


    //UserDetails loadUserByEmail(String username) throws UsernameNotFoundException;
}
