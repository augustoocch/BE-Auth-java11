package com.augustoocc.demo.globant.service.impl;

import com.augustoocc.demo.globant.domain.constants.RolesEnum;
import com.augustoocc.demo.globant.domain.model.Roles;
import com.augustoocc.demo.globant.domain.model.User;
import com.augustoocc.demo.globant.domain.model.repository.RoleRepository;
import com.augustoocc.demo.globant.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


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
