package com.hieunt.ebookapp.payloads;

import lombok.Data;

@Data
public class QueryBookRequest {
    private String keyword;
    private int page;
    private int size;
}
