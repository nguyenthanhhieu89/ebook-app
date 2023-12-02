package com.hieunt.ebookapp.authen;

import com.nimbusds.oauth2.sdk.*;
import com.nimbusds.oauth2.sdk.auth.ClientSecretBasic;
import com.nimbusds.oauth2.sdk.auth.Secret;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.id.State;
import com.nimbusds.oauth2.sdk.pkce.CodeChallengeMethod;
import com.nimbusds.oauth2.sdk.pkce.CodeVerifier;
import com.nimbusds.openid.connect.sdk.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Service
@Slf4j
public class OAuth2OktaService {
    public static final String OKTA_OAUTH2_CLIENT_ID = "0oadlgge68jF9lbsD5d7";
    private static final String OKTA_OAUTH2_CLIENT_SECRET = "N0wrN3v1pQRGqXYdaaUPdzELF43ARHP8N6JY3aDYmy3su8SwTzQqdAFQx5vv8Jx2";
    private static final String SCOPE_OPENID = "openid";
    private static final String SCOPE_PROFILE = "profile";
    private static final String SCOPE_EMAIL = "email";
    private static final String CALLBACK_URL = "http://localhost:8080/authorization-code/callback/okta";
    private static final String AUTH_SERVER_URL = "https://dev-18923263.okta.com/oauth2";
    private static final String OKTA_AUTHORIZE_URL = AUTH_SERVER_URL + "/v1/authorize";
    private static final String OKTA_TOKEN_URL = AUTH_SERVER_URL + "/v1/token";
    private static final String OKTA_USER_INFO = AUTH_SERVER_URL + "/v1/userinfo";

    public String authorizeRequest(final HttpSession session) throws URISyntaxException {
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(
                ResponseType.CODE,
                new Scope(SCOPE_OPENID, SCOPE_EMAIL, SCOPE_PROFILE),
                new ClientID(OKTA_OAUTH2_CLIENT_ID),
                new URI(CALLBACK_URL)
        );
        State state = new State();

        // Generate PKCE code
        CodeVerifier codeVerifier = new CodeVerifier();
        builder.codeChallenge(codeVerifier, CodeChallengeMethod.S256);
        session.setAttribute(state.getValue(), codeVerifier);

        AuthenticationRequest request = builder.endpointURI(new URI(OKTA_AUTHORIZE_URL))
                .state(state)
                .nonce(new Nonce())
                .build();
        return request.toURI().toString();
    }

    public AccessTokenResponse requestOAuth2Token(final HttpSession session, String code, String state) throws URISyntaxException, IOException, ParseException {
        ClientID clientID = new ClientID(OKTA_OAUTH2_CLIENT_ID);
        Secret clientSecret = new Secret(OKTA_OAUTH2_CLIENT_SECRET);

        URI getTokenURI = new URI(OKTA_TOKEN_URL);
        ClientSecretBasic clientSecretBasic = new ClientSecretBasic(clientID, clientSecret);

        CodeVerifier codeVerifier = (CodeVerifier) session.getAttribute(state);
        AuthorizationCodeGrant codeGrant = new AuthorizationCodeGrant(new AuthorizationCode(code), new URI(CALLBACK_URL), codeVerifier);

        // Request Token
        TokenRequest tokenRequest = new TokenRequest(getTokenURI, clientSecretBasic, codeGrant);
        TokenResponse tokenResponse = OIDCTokenResponseParser.parse(tokenRequest.toHTTPRequest().send());
        if (!tokenResponse.indicatesSuccess()) {
            throw new BadCredentialsException("Bad Credentials");
        }
        return tokenResponse.toSuccessResponse();
    }

    public UserInfoSuccessResponse requestUserInfo(AccessTokenResponse accessTokenResponse) throws URISyntaxException, IOException, ParseException {
        // Get User Info
        UserInfoRequest userInfoRequest = new UserInfoRequest(new URI(OKTA_USER_INFO), accessTokenResponse.getTokens().getAccessToken());
        UserInfoResponse userInfoResponse = UserInfoResponse.parse(userInfoRequest.toHTTPRequest().send());
        if (!userInfoResponse.indicatesSuccess()) {
            throw new BadCredentialsException("Bad Credentials");
        }
        return userInfoResponse.toSuccessResponse();
    }
}
