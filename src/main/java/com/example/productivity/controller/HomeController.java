package com.example.productivity.controller;

import com.example.productivity.model.Contact;
import com.example.productivity.model.UserProfile;
import com.example.productivity.repository.UserProfileRepository;
import com.example.productivity.model.Event;
import com.example.productivity.model.UserProfile;
import com.example.productivity.repository.ContactRepository;
import com.example.productivity.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class HomeController {
    @Autowired
    EventRepository eventRepository;
    @Autowired
    ContactRepository contactRepository;
    @Autowired
    CurrentUserController currentUser;
    @Autowired
    UserProfileRepository userProfileRepository;

    @GetMapping("/")
    public ModelAndView contactsPage() {
        ModelAndView modelAndView = new ModelAndView("/page/contacts.html");
//        Iterable<UserProfile> contacts = userProfileRepository.findAll();
        Iterable<Long> contactIds = contactRepository.findUserIdsByUser1Id(currentUser.getCurrentUser().getId());
        Iterable<UserProfile> contacts = userProfileRepository.findAllById(contactIds);
        modelAndView.addObject("contacts", contacts);
        return modelAndView;
    }

    @GetMapping("/home")
    public ModelAndView userHome() {
        ModelAndView modelAndView = new ModelAndView("/page/home.html");
        List<Event> upcomingEvents = eventRepository.findNextUpcomingEventsNative(3);
        List<Long> favouriteContactIds = contactRepository.findFavouritesUserIdsByUser1Id(currentUser.getCurrentUser().getId());
        List<UserProfile> favouriteContactsProfiles = userProfileRepository.findAllById(favouriteContactIds);
        modelAndView.addObject("upcomingEvents", upcomingEvents);
        modelAndView.addObject("favouriteContacts", favouriteContactsProfiles);
        return modelAndView;
    }
}
