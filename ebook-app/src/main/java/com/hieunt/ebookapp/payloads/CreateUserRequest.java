package com.hieunt.ebookapp.payloads;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String email;
    private String password;
}
