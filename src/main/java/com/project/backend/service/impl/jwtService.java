package com.project.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.backend.Entity.Users;
import com.project.backend.service.UserService;

import java.util.Collections;

@Service
public class jwtService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users data = userService.getusername(username);
        if (data!=null) {
            return new User(data.getUserName(), data.getPassword(), Collections.emptyList());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}