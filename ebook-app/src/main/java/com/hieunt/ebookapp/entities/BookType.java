package com.hieunt.ebookapp.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

//Master Data
@Getter
@Setter
@NoArgsConstructor
@Document(collection = "book_types")
public class BookType extends BaseEntity{
    private String id;
    private String typeName;

    public BookType(String typeName) {
        this.typeName = typeName;
    }
}
