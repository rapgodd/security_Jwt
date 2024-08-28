package com.giyeon.security12;

import com.giyeon.security12.domain.Department;
import com.giyeon.security12.domain.Role;
import com.giyeon.security12.domain.User;
import com.giyeon.security12.domain.UserRole;
import com.giyeon.security12.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;
    private final UserRepository userRepository;


    @PostConstruct
    public void init() {
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    public static class InitService {
        private final EntityManager em;
        private final PasswordEncoder passwordEncoder;

        public void dbInit1() {
            Department department = new Department();
            department.setName("IT Department");
            em.persist(department);

            Role roleUser = new Role();
            roleUser.setRoleName("ROLE_USER");
            em.persist(roleUser);

            Role roleAdmin = new Role();
            roleAdmin.setRoleName("ROLE_ADMIN");
            em.persist(roleAdmin);

            // User 1
            User user = new User();
            user.setUsername("john_doe");
            user.setPassword("123");
            user.setEmail("john.doe@cbnu.ac.kr");
            user.setPhoneNumber("123-456-7890");
            user.setDepartment(department);
            String encode = passwordEncoder.encode(user.getPassword());
            user.setPassword(encode);
            em.persist(user);

            // Assign role to user
            UserRole userRole1 = new UserRole();
            userRole1.setUser(user);
            userRole1.setRole(roleUser);
            em.persist(userRole1);

            user.setUserRoles(List.of(userRole1));
            em.persist(user);

            // User 2
            Department department1 = new Department();
            department1.setName("영양학과");
            em.persist(department1);

            User user1 = new User();
            user1.setUsername("기연");
            user1.setPassword("1234");
            user1.setEmail("avce@example.com");
            user1.setPhoneNumber("123-456-7890");
            user1.setDepartment(department1);
            String encode1 = passwordEncoder.encode(user1.getPassword());
            user1.setPassword(encode1);
            em.persist(user1);

            // Assign role to user1
            UserRole userRole2 = new UserRole();
            userRole2.setUser(user1);
            userRole2.setRole(roleUser);
            em.persist(userRole2);

            user1.setUserRoles(List.of(userRole2));
            em.persist(user1);

            // User 3
            Department department2 = new Department();
            department2.setName("경영학과");
            em.persist(department2);

            User user2 = new User();
            user2.setUsername("사자");
            user2.setPassword("1234");
            user2.setEmail("dw@example.com");
            user2.setPhoneNumber("123-456-78902");
            user2.setDepartment(department2);
            String encode2 = passwordEncoder.encode(user2.getPassword());
            user2.setPassword(encode2);
            em.persist(user2);

            // Assign role to user2
            UserRole userRole3 = new UserRole();
            userRole3.setUser(user2);
            userRole3.setRole(roleUser);
            em.persist(userRole3);

            UserRole userRole4 = new UserRole();
            userRole4.setUser(user2);
            userRole4.setRole(roleAdmin);
            em.persist(userRole4);

            user2.setUserRoles(List.of(userRole3, userRole4));
            em.persist(user2);
        }
    }















}
