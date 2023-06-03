package com.hieunt.ebookapp.repositories;

import com.hieunt.ebookapp.entities.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RoleRepository extends MongoRepository<Role,String> {

    Role findByName(String name);
    List<Role> findByIdIn(List<String> roleId);
}
