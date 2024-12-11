package com.example.productivity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HelloWorldController {
    @GetMapping("/example")
    public ModelAndView examplePage() {
        return new ModelAndView("core/example_template.html");
    }
}
