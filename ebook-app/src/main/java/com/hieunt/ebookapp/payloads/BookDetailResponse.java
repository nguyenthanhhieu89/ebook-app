package com.hieunt.ebookapp.payloads;

import com.hieunt.ebookapp.entities.Book;
import lombok.Data;

@Data
public class BookDetailResponse {
    private String name;

    private String intro;

    private String avatar;

    private String url;

    private Long totalView;

    private Integer totalChapter;

    private String authors;

    private String types;

    public BookDetailResponse(Book book, String authors, String types) {
        this.name = book.getName();
        this.intro = book.getIntro();
        this.avatar = book.getAvatar();
        this.url = book.getUrl();
        this.totalView = book.getTotalView();
        this.totalChapter = book.getTotalChapter();
        this.authors = authors;
        this.types = types;
    }
}
