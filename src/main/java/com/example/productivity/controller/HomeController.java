package com.example.productivity.controller;

import com.example.productivity.model.UserProfile;
import com.example.productivity.repository.UserProfileRepository;
import com.example.productivity.model.Event;
import com.example.productivity.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class HomeController {
    @Autowired
    EventRepository eventRepository;

    @Autowired
    UserProfileRepository userProfileRepository;

    @GetMapping("/")
    public ModelAndView userProfile() {
        ModelAndView modelAndView = new ModelAndView("/page/contacts.html");
        Iterable<UserProfile> contacts = userProfileRepository.findAll();
        modelAndView.addObject("contacts", contacts);
        return modelAndView;
    }

    @GetMapping("/home")
    public ModelAndView userHome() {
        ModelAndView modelAndView = new ModelAndView("/page/home.html");
        List<Event> upcomingEvents = eventRepository.findNextUpcomingEventsNative(3);
        modelAndView.addObject("upcomingEvents", upcomingEvents);
        return modelAndView;
    }
}