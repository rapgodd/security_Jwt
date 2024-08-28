package com.giyeon.security12.repository;

import com.giyeon.security12.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"department"})
    List<User> findByEmail(String email);

    @EntityGraph(attributePaths = {"department"})
    User findUserByEmail(String username);

}
