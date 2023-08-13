package com.hieunt.ebookapp.repositories;

import com.hieunt.ebookapp.entities.Book;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CustomBookRepository {
    List<Book> getBookBy(int limit, String orderBy);

    List<Book> getByTypes(Set<String> bookTypes);
}
