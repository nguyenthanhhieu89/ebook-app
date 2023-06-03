package com.hieunt.ebookapp.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "authors")
public class Author extends BaseEntity{
    private String id;
    private String name;

    public Author(String name) {
        this.name = name;
    }
}
