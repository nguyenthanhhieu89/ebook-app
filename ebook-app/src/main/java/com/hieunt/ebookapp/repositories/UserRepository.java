package com.hieunt.ebookapp.repositories;

import com.hieunt.ebookapp.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
}
