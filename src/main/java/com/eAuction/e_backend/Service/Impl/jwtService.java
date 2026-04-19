package com.eAuction.e_backend.Service.Impl;

import com.eAuction.e_backend.Entity.Users;
import com.eAuction.e_backend.Service.UserService;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class jwtService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException {
        Users data = userService.getfromusername(username);
        if (data != null) {
            return new User(
                data.getUserName(),
                data.getPassword(),
                Collections.emptyList()
            );
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
