package com.hieunt.ebookapp.payloads;

import com.hieunt.ebookapp.entities.Book;
import lombok.Data;

import java.util.List;

@Data
public class BookGeneralResponse {
    private Book bookSuggestion;
    private List<Book> latestBooks;
    private List<Book> hottestBooks;

}
