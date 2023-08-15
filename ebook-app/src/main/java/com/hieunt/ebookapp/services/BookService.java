package com.hieunt.ebookapp.services;

import com.hieunt.ebookapp.entities.Author;
import com.hieunt.ebookapp.entities.Book;
import com.hieunt.ebookapp.entities.BookType;
import com.hieunt.ebookapp.payloads.*;
import com.hieunt.ebookapp.repositories.AuthorRepository;
import com.hieunt.ebookapp.repositories.BookRepository;
import com.hieunt.ebookapp.repositories.BookTypeRepository;
import com.hieunt.ebookapp.repositories.CustomBookRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    CustomBookRepository customBookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookTypeRepository bookTypeRepository;

    public Author addAuthor(AddAuthorRequest request) {
        if (request == null || !(StringUtils.hasText(request.getAuthorName()))) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }
        String authorName =request.getAuthorName();
        Optional<Author> author = authorRepository.findByName(authorName);
        if (author.isPresent()){
            throw new RuntimeException("Author is exist");
        }
        return authorRepository.save(new Author(authorName));
    }

    public List<Author> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        authors.sort(Comparator.comparing(Author::getName));
        return authors;
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

    public BookGeneralResponse getGeneralBooks() {
        List<Book> latestBook = customBookRepository.getBookBy(12, "updatedDate");
        List<Book> hottestBook = customBookRepository.getBookBy(12, "totalView");
        BookGeneralResponse response = new BookGeneralResponse();
        if (!latestBook.isEmpty()) {
            response.setLatestBooks(latestBook);
            response.setBookSuggestion(latestBook.get(0));
        }
        if (!hottestBook.isEmpty()) {
            response.setHottestBooks(hottestBook);
        }
        return response;
    }
    public BookHottestResponse getListBookHottest(){
        List<Book> hottestBook = customBookRepository.getBookBy(15,"totalView");
        BookHottestResponse response = new BookHottestResponse();
        if (!hottestBook.isEmpty()) {
            response.setHottestBook(hottestBook);
        }
        return response;
    }

    public BookDetailResponse getBookById(String id){
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isEmpty()){
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
        Book book = bookOptional.get();
        Set<String> bookTypes = bookTypeRepository.findByIdIn(new HashSet<>(book.getBookTypes()))
                .stream().map(BookType::getTypeName).collect(Collectors.toSet());
        Set<String> authors = authorRepository.findByIdIn(new HashSet<>(book.getAuthorIds()))
                .stream().map(Author::getName).collect(Collectors.toSet());
        String types = String.join(", ", bookTypes);
        String author = String.join(", ",authors);
        return new BookDetailResponse(book,author,types);
    }

    public void updateTotalView(String bookID){
        Optional<Book> optionalBook = bookRepository.findById(bookID);
        Book book = optionalBook.get();
        book.setTotalView(book.getTotalView() + 1);
        bookRepository.save(book);
    }

    public List<Book> getBookSameType(String bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Book book = optionalBook.get();
        Set<String> bookTypes = book.getBookTypes();
        return customBookRepository.getByTypes(bookTypes);
    }

    public QueryBookResponse queryBookBy(QueryBookRequest request) {
        List<Book> books = customBookRepository.getBookListBy(request);
        int total = customBookRepository.countBookListBy(request);
        return new QueryBookResponse(books, total);
    }
}
