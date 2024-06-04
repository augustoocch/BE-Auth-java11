package com.augustoocc.demo.globant.model.service.impl;


import com.augustoocc.demo.globant.model.repository.entity.Roles;
import com.augustoocc.demo.globant.model.repository.entity.User;
import com.augustoocc.demo.globant.model.repository.RoleRepository;
import com.augustoocc.demo.globant.model.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
@AllArgsConstructor
public class UserDetailedService implements UserDetailsService {

    private final UserService usersService;
    private final RoleRepository roleRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.usersService.findByEmail(username);

        Integer roleUsr = user.getRole();
        Roles role = roleRepository.findById(roleUsr)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        Set<SimpleGrantedAuthority> roles = Set.of(new SimpleGrantedAuthority(role.getRole()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), roles);
    }
}
