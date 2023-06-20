package com.hieunt.ebookapp.repositories;

import com.hieunt.ebookapp.entities.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomBookRepository {
    List<Book> getBookBy(int limit, String orderBy);
}
