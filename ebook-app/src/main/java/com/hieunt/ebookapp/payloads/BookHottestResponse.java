package com.hieunt.ebookapp.payloads;

import com.hieunt.ebookapp.entities.Book;
import lombok.Data;

import java.util.List;

@Data
public class BookHottestResponse {
    private List<Book> hottestBook;
}
