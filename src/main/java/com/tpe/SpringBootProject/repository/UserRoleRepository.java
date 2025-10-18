package com.tpe.SpringBootProject.repository;

import com.tpe.SpringBootProject.domain.UserRole;
import com.tpe.SpringBootProject.domain.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    Optional<UserRole> findByRole(Role role);
}
