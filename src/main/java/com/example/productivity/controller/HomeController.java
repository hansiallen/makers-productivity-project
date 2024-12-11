package com.example.productivity.controller;

import com.example.productivity.model.UserProfile;
import com.example.productivity.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {

    @Autowired
    UserProfileRepository userProfileRepository;

    @GetMapping("/")
    public ModelAndView userProfile() {
        ModelAndView modelAndView = new ModelAndView("/page/contacts.html");
        Iterable<UserProfile> contacts = userProfileRepository.findAll();
        modelAndView.addObject("contacts", contacts);
        return modelAndView;
    }
}