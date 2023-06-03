package com.hieunt.ebookapp.repositories;

import com.hieunt.ebookapp.entities.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {
}
