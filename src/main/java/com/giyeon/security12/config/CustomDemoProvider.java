package com.giyeon.security12.config;

import com.giyeon.security12.domain.User;
import com.giyeon.security12.domain.UserRole;
import com.giyeon.security12.repository.UserRepository;
import com.giyeon.security12.repository.UserRoleRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomDemoProvider implements AuthenticationProvider {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository roleRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = (String) authentication.getPrincipal();
        String password = authentication.getCredentials().toString();

        User userByEmail = userRepository.findUserByEmail(username);
        List<UserRole> userRoleByUser = roleRepository.findUserRoleByUser(userByEmail);
        userByEmail.setUserRoles(userRoleByUser);

        if (userByEmail == null) {
            throw new IllegalArgumentException("잘못된 자격증명입니다.");
        }else {
            if (passwordEncoder.matches(password, userByEmail.getPassword())) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password, userByEmail.getAuthorities());
                return usernamePasswordAuthenticationToken;
            }else {
                throw new IllegalArgumentException("잘못된 비밀번호입니다.");
            }
        }
    }



    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
