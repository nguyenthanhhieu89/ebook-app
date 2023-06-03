package com.hieunt.ebookapp.controllers;

import com.hieunt.ebookapp.entities.User;
import com.hieunt.ebookapp.payloads.ChangePassRequest;
import com.hieunt.ebookapp.payloads.CreateUserRequest;
import com.hieunt.ebookapp.payloads.LoginRequest;
import com.hieunt.ebookapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;

@Controller
@RequestMapping("/authen")
public class AuthController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/login-page")
    public String getLogin() {
        return "login";
    }

    @GetMapping(value = "/register-page")
    public String getRegister() {
        return "register";
    }

    @GetMapping(value = "/change-pass-page")
    public String changePassPage() {

        return "change-pass";
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            UserDetails userDetails = userService.authenticatedLogin(request);
            return ResponseEntity.ok(userDetails);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (AuthenticationServiceException ex) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/register")
    public ResponseEntity<User> createNewUser(@RequestBody CreateUserRequest request) {
        try {
            User user = userService.createNewUser(request);
            return ResponseEntity.ok(user);
        } catch (HttpClientErrorException exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/change-pass")
    public ResponseEntity<?> changePass(@RequestBody ChangePassRequest request) {
        try {
            userService.updatePassword(request);
            return ResponseEntity.ok(null);
        } catch (HttpClientErrorException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
