package com.hieunt.ebookapp.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GithubUserInfoResponse {
    @JsonProperty("login")
    private String username;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("type")
    private String role;
}
