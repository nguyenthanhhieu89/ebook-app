package com.hieunt.ebookapp.repositories;

import com.hieunt.ebookapp.entities.BookType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Set;

public interface BookTypeRepository extends MongoRepository<BookType, String> {
    BookType findByTypeName(String name);
    Set<BookType> findByTypeNameIn(Set<String> typeNames);
}
