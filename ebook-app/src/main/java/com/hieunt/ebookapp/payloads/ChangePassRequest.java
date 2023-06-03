package com.hieunt.ebookapp.payloads;

import lombok.Data;

@Data
public class ChangePassRequest {

    private String email;
    private String oldPassword;
    private String newPassword;
}
