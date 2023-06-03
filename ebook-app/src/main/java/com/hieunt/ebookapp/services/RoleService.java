package com.hieunt.ebookapp.services;

import com.hieunt.ebookapp.authen.RoleEnum;
import com.hieunt.ebookapp.entities.Role;
import com.hieunt.ebookapp.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void initRole() {
        Role roleUser = roleRepository.findByName(RoleEnum.USER.name());
        if (roleUser == null) {
            Role role = new Role();
            role.setName(RoleEnum.USER.name());
            roleRepository.save(role);
        }

        Role roleAdmin = roleRepository.findByName(RoleEnum.ADMIN.name());
        if (roleAdmin == null) {
            Role role1 = new Role();
            role1.setName(RoleEnum.ADMIN.name());
            roleRepository.save(role1);
        }

    }
}
