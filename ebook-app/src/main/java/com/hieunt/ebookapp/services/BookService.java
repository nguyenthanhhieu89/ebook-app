package com.hieunt.ebookapp.services;

import com.hieunt.ebookapp.entities.Author;
import com.hieunt.ebookapp.payloads.AddAuthorRequest;
import com.hieunt.ebookapp.repositories.AuthorRepository;
import com.hieunt.ebookapp.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    public Author addAuthor(AddAuthorRequest request) {
        if (request == null || !(StringUtils.hasText(request.getAuthorName()))) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }
        return authorRepository.save(new Author(request.getAuthorName()));
    }
}
