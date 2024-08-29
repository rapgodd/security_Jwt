package com.giyeon.security12.controller;

import com.giyeon.security12.domain.User;
import com.giyeon.security12.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User customer) {

        ResponseEntity response = null;
        try {
            String password = customer.getPassword();
            String email = customer.getEmail();
            password = passwordEncoder.encode(password);
            customer.setPassword(password);
            customer.setEmail(email);

            User user = customerRepository.save(customer);
            if (user.getId() > 0) {
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("Given user details are successfully registered");
            }
        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        return response;
    }

    @GetMapping("/")
    public String login() {

        return "login~~~";
    }

    @PostMapping("/login")
    public ResponseEntity<String> afterLogin() {
        return ResponseEntity.ok("login~~~");
    }

}
