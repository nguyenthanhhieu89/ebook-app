package com.hieunt.ebookapp.controllers;

import com.hieunt.ebookapp.entities.Author;
import com.hieunt.ebookapp.entities.Book;
import com.hieunt.ebookapp.payloads.AddAuthorRequest;
import com.hieunt.ebookapp.payloads.BookDetailResponse;
import com.hieunt.ebookapp.payloads.BookGeneralResponse;
import com.hieunt.ebookapp.payloads.BookHottestResponse;
import com.hieunt.ebookapp.services.BookService;
import com.hieunt.ebookapp.services.BookTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    BookTypeService bookTypeService;

    @PostMapping(value = "/authors")
    public ResponseEntity<Author> createAuthor(@RequestBody AddAuthorRequest request) {
        try {
            Author author = bookService.addAuthor(request);
            return new ResponseEntity<>(author, HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/books")
    public ResponseEntity<?> createBook(@RequestBody Book book) {
        try {
            return ResponseEntity.ok(bookService.insertNewBook(book));
        } catch (HttpClientErrorException ex) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/book-types")
    public ResponseEntity<?> getAllBookType() {
        try {
            return ResponseEntity.ok(bookTypeService.getAllBookType());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/authors")
    public ResponseEntity<?> getAllAuthors() {
        try {
            return new ResponseEntity<>(bookService.getAllAuthors(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/books/general")
    public ResponseEntity<BookGeneralResponse> getGeneralResponse() {
        try {
            return new ResponseEntity<>(bookService.getGeneralBooks(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/books/list-hottest")
    public ResponseEntity<BookHottestResponse> getListBookHottest(){
        try {
            return new ResponseEntity<>(bookService.getListBookHottest(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/books/{id}")
    public ResponseEntity<BookDetailResponse> getBookDetailBy(@PathVariable String id){
        try {
            BookDetailResponse response = bookService.getBookById(id);
            return ResponseEntity.ok(response);
        }catch (HttpClientErrorException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
