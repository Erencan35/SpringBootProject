package com.tpe.SpringBootProject.security.service;



import com.tpe.SpringBootProject.domain.User;
import com.tpe.SpringBootProject.domain.UserRole;
import com.tpe.SpringBootProject.exception.ResourceNotFoundException;
import com.tpe.SpringBootProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Username not found: " + username)
        );

        //if (user != null){} Optional olmasaydi if ile null check yapilirdi

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
//                true,
//                true,
//                true,
//                true,
                buildGrantedAuthorities(user.getRoles())
        );
    }

    private static List<SimpleGrantedAuthority> buildGrantedAuthorities(final Set<UserRole> roles){
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();

        for (UserRole role : roles){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole().name()));
        }

        return grantedAuthorities;
    }
}