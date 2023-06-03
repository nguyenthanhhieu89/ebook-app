package com.hieunt.ebookapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class EbookAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbookAppApplication.class, args);
    }

}
