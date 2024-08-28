package com.giyeon.security12.service;

import com.giyeon.security12.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

//    public User findUserFromUsername(){
//        User user = new User();
//        user.setUsername("admin");
//        Optional<User> byUsername = userRepository.findUserByUsername(user.getUsername());
//        return byUsername;
//    }
//
//    public List<User> findAllUsers(){
//        List<User> all = userRepository.findAll();
//        return all;
//    }


}
