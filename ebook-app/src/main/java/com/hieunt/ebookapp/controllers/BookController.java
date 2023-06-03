package com.hieunt.ebookapp.controllers;

import com.hieunt.ebookapp.entities.Author;
import com.hieunt.ebookapp.payloads.AddAuthorRequest;
import com.hieunt.ebookapp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping(value = "/authors")
    public ResponseEntity<Author> createAuthor(@RequestBody AddAuthorRequest request) {
        try {
            Author author = bookService.addAuthor(request);
            return new ResponseEntity<>(author, HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
