package com.example.productivity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {

    @GetMapping("/")
    public ModelAndView userProfile() {
        ModelAndView modelAndView = new ModelAndView("/page/contacts.html");
        return modelAndView;
    }
}