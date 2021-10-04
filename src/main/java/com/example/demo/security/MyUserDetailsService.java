package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.controller.UserController;
import com.example.demo.entities.User;


@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserController userController;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userController.findUserByUserName(userName);
        return new MyUserDetails(user);
    }

}
