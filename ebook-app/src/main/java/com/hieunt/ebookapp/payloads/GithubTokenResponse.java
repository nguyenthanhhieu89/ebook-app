package com.hieunt.ebookapp.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GithubTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;
}
