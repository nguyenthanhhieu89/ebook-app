package com.hieunt.ebookapp.repositories;

import com.hieunt.ebookapp.entities.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthorRepository extends MongoRepository<Author,String> {
}
