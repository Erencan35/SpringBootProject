package com.tpe.SpringBootProject.service;


import com.tpe.SpringBootProject.domain.User;
import com.tpe.SpringBootProject.domain.UserRole;
import com.tpe.SpringBootProject.domain.enums.Role;
import com.tpe.SpringBootProject.dto.UserRegisterDTO;
import com.tpe.SpringBootProject.repository.UserRepository;
import com.tpe.SpringBootProject.security.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;

    public Map<String, Object> saveUser(UserRegisterDTO dto) {
        User newUser = new User();
        newUser.setFirstName(dto.getFirstName());
        newUser.setLastName(dto.getLastName());
        newUser.setUsername(dto.getUsername()); //unique!!! It must be checked
        //newUser.setPassword(dto.getPassword()); // Password comes from DTO in plain text. For example -> 123456
        newUser.setPassword(passwordEncoder.encode(dto.getPassword())); // Hashed Password

        User savedUser = userRepository.save(newUser);

        Map<String, Object> map = new HashMap<>();
        map.put("message", "Register successful.");
        map.put("user", savedUser);

        return map;
    }
}