package com.giyeon.security12.repository;

import com.giyeon.security12.domain.User;
import com.giyeon.security12.domain.UserRole;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @EntityGraph(attributePaths = {"user","role"})
    List<UserRole> findUserRoleByUser(User user);


}
