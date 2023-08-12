package com.hieunt.ebookapp.controllers;

import com.hieunt.ebookapp.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UIController {
    @Autowired
    BookRepository bookRepository;
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

    @GetMapping(value = "/book-library")
    public String getBookLibrary(){

        return "book-library";
    }

    @GetMapping(value = "/book-page/{id}")
    public ModelAndView getBookInforPage(@PathVariable String id){
        ModelAndView modelAndView = new ModelAndView("book-info");
        if (bookRepository.existsById(id)){
            modelAndView.addObject("bookId",id);
            return modelAndView;
        }
        throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
    }



}
