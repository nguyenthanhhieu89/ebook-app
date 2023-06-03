package com.hieunt.ebookapp.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "books")
public class Book extends BaseEntity {
    private String id;

    private String name;

    private String intro;

    //File PNG/JPG/....
    private String avatar;

    //FIle PDF
    private String url;

    private Long totalView;

    private Integer totalChapter;

    private Set<String> bookTypes;

    private Set<String> authorIds;
}
