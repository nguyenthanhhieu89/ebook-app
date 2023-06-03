package com.hieunt.ebookapp.authen;

import com.hieunt.ebookapp.entities.Role;
import com.hieunt.ebookapp.entities.User;
import com.hieunt.ebookapp.repositories.RoleRepository;
import com.hieunt.ebookapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User is not exist");
        }
        List<String> roleId = user.getRoles();
        List<String> roleNames = roleRepository.findByIdIn(roleId).stream().map(Role::getName).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), roleNames.stream()
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }
}
