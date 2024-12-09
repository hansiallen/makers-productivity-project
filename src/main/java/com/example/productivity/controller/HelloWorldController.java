package com.example.productivity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HelloWorldController {
    @GetMapping("/")
    public String home(){
        return "home page";
    }
}
