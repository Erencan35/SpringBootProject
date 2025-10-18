package com.tpe.SpringBootProject.security.service;

import com.tpe.SpringBootProject.domain.UserRole;
import com.tpe.SpringBootProject.domain.enums.Role;
import com.tpe.SpringBootProject.exception.ResourceNotFoundException;
import com.tpe.SpringBootProject.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRoleService {
    //    @Autowired
//  private UserRoleRepository userRoleRepository;
    private final UserRoleRepository userRoleRepository;

    public UserRole findUserRoleByRole(Role role){
        return userRoleRepository.findByRole(role).orElseThrow(
                () -> new ResourceNotFoundException("Role not found: "+ role)
        );
    }

}
