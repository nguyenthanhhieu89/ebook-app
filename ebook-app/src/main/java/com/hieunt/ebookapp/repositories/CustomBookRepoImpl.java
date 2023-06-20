package com.hieunt.ebookapp.repositories;

import com.hieunt.ebookapp.entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomBookRepoImpl implements CustomBookRepository {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public CustomBookRepoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Book> getBookBy(int limit, String orderBy) {
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.DESC, orderBy));
        query.with(PageRequest.of(0, limit));
        return mongoTemplate.find(query, Book.class);
    }
}
