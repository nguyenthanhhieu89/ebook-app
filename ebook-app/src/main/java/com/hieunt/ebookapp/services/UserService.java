package com.hieunt.ebookapp.services;

import com.hieunt.ebookapp.entities.User;
import com.hieunt.ebookapp.payloads.ChangePassRequest;
import com.hieunt.ebookapp.payloads.CreateUserRequest;
import com.hieunt.ebookapp.payloads.LoginRequest;
import com.hieunt.ebookapp.payloads.ResetPassRequest;
import com.hieunt.ebookapp.repositories.RoleRepository;
import com.hieunt.ebookapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    SimpleMailService simpleMailService;

    @PostConstruct
    public void initAdmin() {
        User user = userRepository.findByEmail("admin@gmail.com");
        if (user == null) {
            user = new User();
            user.setEmail("admin@gmail.com");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setRoles(List.of(roleRepository.findByName("ADMIN").getId()));
            userRepository.save(user);
        }
    }

    public User createNewUser(CreateUserRequest request) {
        if (request == null || !StringUtils.hasText(request.getEmail()) || !StringUtils.hasText(request.getPassword())) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }
        User user = userRepository.findByEmail(request.getEmail());
        if (user != null) {
            throw new RuntimeException("Email is exists");
        }

        user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        List<String> roleUser = List.of(roleRepository.findByName("USER").getId());
        user.setRoles(roleUser);
        return userRepository.save(user);
    }

    public UserDetails authenticatedLogin(LoginRequest request) {
        if (request == null || !StringUtils.hasText(request.getEmail()) || !StringUtils.hasText(request.getPassword())) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }

        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return (UserDetails) authentication.getPrincipal();
        } catch (Exception e) {
            throw new AuthenticationServiceException(e.getMessage());
        }
    }

    public void updatePassword(ChangePassRequest request) {
        if (request == null || !StringUtils.hasText(request.getEmail()) || !StringUtils.hasText(request.getNewPassword()) || !StringUtils.hasText(request.getOldPassword())) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getOldPassword()));
            SecurityContextHolder.getContext().setAuthentication(null);
            User user = userRepository.findByEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);
        } catch (Exception e) {
            throw new AuthenticationServiceException(e.getMessage());
        }
    }

    public void resetPass(ResetPassRequest request) {
        if (request == null || !StringUtils.hasText(request.getEmail())) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }
        String email = request.getEmail();
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }
        String newpass = String.valueOf(System.currentTimeMillis());
        simpleMailService.sendMailMessage(email, "Đặt lại mật khẩu", "Mật khẩu mới của bạn là: " + newpass);
        user.setPassword(passwordEncoder.encode(newpass));
        userRepository.save(user);
    }
}
