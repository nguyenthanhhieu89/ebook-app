package com.hieunt.ebookapp.authen;

import com.hieunt.ebookapp.payloads.GithubTokenResponse;
import com.hieunt.ebookapp.payloads.GithubUserInfoResponse;
import com.nimbusds.oauth2.sdk.ResponseType;
import com.nimbusds.oauth2.sdk.Scope;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.id.State;
import com.nimbusds.openid.connect.sdk.AuthenticationRequest;
import com.nimbusds.openid.connect.sdk.Nonce;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@Slf4j
public class OAuth2Service {

    public static final String GITHUB_OAUTH2_CLIENT_ID = "fb5ba42477df3b025569";
    private static final String GITHUB_OAUTH2_CLIENT_SECRET = "eeedd71d578dc03852ecfedb0d3afb45d4802bfc";


    private static final String SCOPE_OPENID = "openid";
    private static final String CALLBACK_URL = "http://localhost:8080/authorization-code/callback";
    private static final String GITHUB_AUTHORIZE_URL = "https://github.com/login/oauth/authorize";
    private static final String GITHUB_ACCESS_TOKEN_PATH = "https://github.com/login/oauth/access_token";
    private static final String GITHUB_USER_INFO_PATH = "https://api.github.com/user";

    public String authorizeRequest() throws URISyntaxException {
        AuthenticationRequest.Builder requestBuilder = new AuthenticationRequest.Builder(ResponseType.CODE, new Scope(SCOPE_OPENID), new ClientID(GITHUB_OAUTH2_CLIENT_ID), new URI(CALLBACK_URL));
        State state = new State();


        AuthenticationRequest request = requestBuilder.endpointURI(new URI(GITHUB_AUTHORIZE_URL)).state(state).nonce(new Nonce()).build();
        return request.toURI().toString();
    }

    public GithubUserInfoResponse requestOAuth2Token(String code) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            // request Token from authorization code
            String githubTokenRequest = GITHUB_ACCESS_TOKEN_PATH.concat("?client_id=").concat(GITHUB_OAUTH2_CLIENT_ID).concat("&client_secret=").concat(GITHUB_OAUTH2_CLIENT_SECRET).concat("&code=").concat(code).concat("&redirect_uri=").concat(CALLBACK_URL);

            HttpHeaders requestTokenHeader = new HttpHeaders();
            requestTokenHeader.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(requestTokenHeader);

            GithubTokenResponse tokenResponse = restTemplate.postForObject(githubTokenRequest, httpEntity, GithubTokenResponse.class);

            // request user info from access token
            assert tokenResponse != null;
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(tokenResponse.getAccessToken());
            ResponseEntity<GithubUserInfoResponse> response = restTemplate.exchange(GITHUB_USER_INFO_PATH, HttpMethod.GET, new HttpEntity<>(headers), GithubUserInfoResponse.class);
            return response.getBody();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
