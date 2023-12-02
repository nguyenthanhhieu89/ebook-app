package com.hieunt.ebookapp.controllers;

import com.hieunt.ebookapp.authen.OAuth2GithubService;
import com.hieunt.ebookapp.authen.OAuth2OktaService;
import com.hieunt.ebookapp.payloads.GithubUserInfoResponse;
import com.hieunt.ebookapp.payloads.LoginRequest;
import com.hieunt.ebookapp.services.UserService;
import com.nimbusds.oauth2.sdk.AccessTokenResponse;
import com.nimbusds.openid.connect.sdk.UserInfoSuccessResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.Objects;

import static com.hieunt.ebookapp.authen.OAuth2GithubService.GITHUB_OAUTH2_CLIENT_ID;
import static com.hieunt.ebookapp.authen.OAuth2OktaService.OKTA_OAUTH2_CLIENT_ID;

@Controller
@Slf4j
public class OAuth2Controller {
    @Autowired
    private OAuth2GithubService oAuth2GithubService;

    @Autowired
    private OAuth2OktaService oAuth2OktaService;

    @Autowired
    private UserService userService;


    //===============================================
    // OAuth2 using github authorization server
    //===============================================
    @GetMapping("/oauth2/authorization/github")
    public RedirectView githubAuthorizeRedirect() {
        try {
            String githubAuthUrl = oAuth2GithubService.authorizeRequest();
            return new RedirectView(githubAuthUrl);
        } catch (Exception e) {
            return new RedirectView("http://localhost:8080/authen/login-page");
        }
    }

    @GetMapping("/authorization-code/callback")
    public RedirectView authGithubCallback(@RequestParam(required = false) String code,
                                           @RequestParam(required = false) String state) {
        if (!StringUtils.hasText(code)) {
            return new RedirectView("http://localhost:8080/authen/login-page");
        }

        try {
            GithubUserInfoResponse userInfoResponse = oAuth2GithubService.requestOAuth2Token(code);
            if (Objects.isNull(userInfoResponse)) {
                return new RedirectView("http://localhost:8080/authen/login-page");
            }
            // Create User context
            String username = userInfoResponse.getUsername();
            userService.createOAuth2User(username, GITHUB_OAUTH2_CLIENT_ID);

            // Login
            userService.authenticatedLogin(new LoginRequest(username, GITHUB_OAUTH2_CLIENT_ID));

            return new RedirectView("http://localhost:8080/home-page");
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new RedirectView("http://localhost:8080/authen/login-page");
        }
    }

    //===============================================
    // OAuth2 using github authorization server
    //===============================================
    @GetMapping("/oauth2/authorization/okta")
    public RedirectView oktaAuthorizeRedirect(final HttpSession session) {
        try {
            String githubAuthUrl = oAuth2OktaService.authorizeRequest(session);
            return new RedirectView(githubAuthUrl);
        } catch (Exception e) {
            return new RedirectView("http://localhost:8080/authen/login-page");
        }
    }

    @GetMapping("/authorization-code/callback/okta")
    public RedirectView authOktaCallback(
            final HttpSession session,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String state) {
        if (!StringUtils.hasText(code) || !StringUtils.hasText(state)) {
            return new RedirectView("http://localhost:8080/authen/login-page");
        }

        try {
            AccessTokenResponse accessTokenResponse = oAuth2OktaService.requestOAuth2Token(session, code, state);
            if (Objects.isNull(accessTokenResponse)) {
                return new RedirectView("http://localhost:8080/authen/login-page");
            }

            // Create User context
            UserInfoSuccessResponse userInfo = oAuth2OktaService.requestUserInfo(accessTokenResponse);

            String username = userInfo.getUserInfo().getEmailAddress();
            userService.createOAuth2User(username, OKTA_OAUTH2_CLIENT_ID);

            // Login
            userService.authenticatedLogin(new LoginRequest(username, OKTA_OAUTH2_CLIENT_ID));

            return new RedirectView("http://localhost:8080/home-page");
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new RedirectView("http://localhost:8080/authen/login-page");
        }
    }
}
