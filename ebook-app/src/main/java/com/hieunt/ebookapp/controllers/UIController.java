package com.hieunt.ebookapp.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UIController {
    @GetMapping(value = {"/", "/home-page"})
    public String getIndex(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String email = authentication.getName();
            if (StringUtils.hasText(email)) {
                request.getSession().setAttribute("email", email);
            }
        }
        return "home";
    }

    @GetMapping(value = "/admin/insert-book-page")
    public String getInsertBookPage() {

        return "insertBook";
    }

}
