package com.hieunt.ebookapp.repositories;

import com.hieunt.ebookapp.entities.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.Set;

public interface AuthorRepository extends MongoRepository<Author,String> {

    Set<Author> findByIdIn(Set<String> ids);
    Optional<Author> findByName(String name);
}
