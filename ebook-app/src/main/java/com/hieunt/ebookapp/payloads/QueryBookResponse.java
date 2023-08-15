package com.hieunt.ebookapp.payloads;

import com.hieunt.ebookapp.entities.Book;
import lombok.Data;

import java.util.List;

@Data
public class QueryBookResponse {
    private List<Book> books;
    private int total;

    public QueryBookResponse(List<Book> books, int total) {
        this.books = books;
        this.total = total;
    }
}
