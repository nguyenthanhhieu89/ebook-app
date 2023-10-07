package com.hieunt.ebookapp.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity{
    private String id;
    private String email;
    private String password;
    private List<String> roles;
    private String oauthType;
}
