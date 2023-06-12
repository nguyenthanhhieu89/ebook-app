package com.hieunt.ebookapp.services;

import com.hieunt.ebookapp.entities.Author;
import com.hieunt.ebookapp.entities.Book;
import com.hieunt.ebookapp.entities.BookType;
import com.hieunt.ebookapp.payloads.AddAuthorRequest;
import com.hieunt.ebookapp.repositories.AuthorRepository;
import com.hieunt.ebookapp.repositories.BookRepository;
import com.hieunt.ebookapp.repositories.BookTypeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashSet;
import java.util.Set;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookTypeRepository bookTypeRepository;

    public Author addAuthor(AddAuthorRequest request) {
        if (request == null || !(StringUtils.hasText(request.getAuthorName()))) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }
        return authorRepository.save(new Author(request.getAuthorName()));
    }

    public Set<Author> getAllAuthors() {

        return new HashSet<>(authorRepository.findAll());
    }

    public Book insertNewBook(Book bookRequest) {
        if (bookRequest == null || !(StringUtils.hasText(bookRequest.getName()))
                || !(StringUtils.hasText(bookRequest.getAvatar())) || !(StringUtils.hasText(bookRequest.getUrl()))
                || bookRequest.getTotalChapter() == null || bookRequest.getBookTypes().isEmpty()
                || bookRequest.getAuthorIds().isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }

        Set<BookType> bookTypes = bookTypeRepository.findByIdIn(bookRequest.getBookTypes());
        Set<Author> authors = authorRepository.findByIdIn(bookRequest.getAuthorIds());
        if (bookTypes.isEmpty() || bookTypes.size() != bookRequest.getBookTypes().size() ||
                authors.isEmpty() || authors.size() != bookRequest.getAuthorIds().size()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }

        Book book = new Book();
        BeanUtils.copyProperties(bookRequest, book);
        return bookRepository.save(book);
    }
}
