package com.giyeon.security12.service;//package com.security.basic.demo.security.service;
//
//import com.security.basic.demo.security.domain.User;
//import com.security.basic.demo.security.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Transactional(readOnly = true)
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//
//        // 사용자 정보를 찾는다. 사용자 이름이 유니크하다는 가정 하에 Optional을 사용
//        User user = userRepository.findUserByEmail(username);
//        if (user == null) {
//            throw new UsernameNotFoundException(username);
//        }
//
//
//        String username1 = user.getEmail();
//        String password = user.getPassword();
//
//        // 사용자의 권한 설정
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority(user.getRole()));  // 예시로 role을 사용
//
//        return new org.springframework.security.core.userdetails.User(username1, password, authorities);
//    }
//}

