package com.hieunt.ebookapp.controllers;

import com.hieunt.ebookapp.authen.OAuth2Service;
import com.hieunt.ebookapp.payloads.GithubUserInfoResponse;
import com.hieunt.ebookapp.payloads.LoginRequest;
import com.hieunt.ebookapp.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URISyntaxException;
import java.util.Objects;

import static com.hieunt.ebookapp.authen.OAuth2Service.GITHUB_OAUTH2_CLIENT_ID;

@Controller
@Slf4j
public class OAuth2Controller {
    @Autowired
    private OAuth2Service oAuth2Service;

    @Autowired
    private UserService userService;


    @GetMapping("/oauth2/authorization/github")
    public RedirectView githubAuthorizeRedirect() {
        try {
            String githubAuthUrl = oAuth2Service.authorizeRequest();
            return new RedirectView(githubAuthUrl);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/authorization-code/callback")
    public RedirectView authenticationCallback(@RequestParam(required = false) String code,
                                               @RequestParam(required = false) String state) {
        if (!StringUtils.hasText(code)) {
            return new RedirectView("http://localhost:8080/authen/login-page");
        }

        try {
            GithubUserInfoResponse userInfoResponse = oAuth2Service.requestOAuth2Token(code);
            if (Objects.isNull(userInfoResponse)) {
                return new RedirectView("http://localhost:8080/authen/login-page");
            }
            // Create User context
            String username = userInfoResponse.getUsername();
            userService.createOAuth2User(username);

            // Login
            userService.authenticatedLogin(new LoginRequest(username, GITHUB_OAUTH2_CLIENT_ID));

            return new RedirectView("http://localhost:8080/home-page");
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new RedirectView("http://localhost:8080/authen/login-page");
        }
    }
}
