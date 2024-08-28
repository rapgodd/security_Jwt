package com.giyeon.security12.controller;

import com.giyeon.security12.domain.User;
import com.giyeon.security12.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/user/information")
    public User userInformation(Authentication authentication) {
        String email = authentication.getName();
        List<User> userList = userRepository.findByEmail(email);
        User user = userList.get(0);
        return user;
    }

}
