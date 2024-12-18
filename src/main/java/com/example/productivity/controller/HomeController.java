package com.example.productivity.controller;

import com.example.productivity.model.Event;
import com.example.productivity.model.Notification;
import com.example.productivity.model.UserProfile;
import com.example.productivity.repository.NotificationRepository;
import com.example.productivity.repository.UserProfileRepository;
import com.example.productivity.repository.ContactRepository;
import com.example.productivity.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

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
    @Autowired
    NotificationRepository notificationRepository;

    @GetMapping("/contacts")
    public ModelAndView contactsPage() {
        ModelAndView modelAndView = new ModelAndView("/page/contacts.html");
        Iterable<Long> contactIds = contactRepository.findUserIdsByUser1Id(currentUser.getCurrentUser().getId());
        Iterable<UserProfile> contacts = userProfileRepository.findAllById(contactIds);
        long contactsCount = StreamSupport.stream(contactIds.spliterator(), false).count();
        modelAndView.addObject("contacts", contacts);
        modelAndView.addObject("contactsCount", Long.toString(contactsCount));
        return modelAndView;
    }

    @GetMapping("/")
    public ModelAndView userHome() {
        ModelAndView modelAndView = new ModelAndView("/page/home.html");
        List<Notification> notifications = notificationRepository.findByReceiverIdAndIsReadFalseOrderByCreatedAtDesc(currentUser.getCurrentUser().getId());
        List<Event> upcomingEvents = eventRepository.findNextUpcomingEvents(3, currentUser.getCurrentUser().getId());
        List<Long> favouriteContactIds = contactRepository.findFavouritesUserIdsByUser1Id(currentUser.getCurrentUser().getId());
        List<UserProfile> favouriteContactsProfiles = userProfileRepository.findAllById(favouriteContactIds);

        Map<Long, String> redirectMap = new HashMap<>();
        for (Notification notification : notifications) {
            String redirectUrl = "";
            String type = notification.getType();

            if (type.equals("event")) {
                redirectUrl += "/events/" + notification.getEventId();
            } else if (type.equals("contact")) {
                redirectUrl += "/profile/" + notification.getSenderId();
            } else {
                redirectUrl += "/";
            }

            redirectMap.put(notification.getId(), redirectUrl);
        }

        modelAndView.addObject("notifications", notifications);
        modelAndView.addObject("upcomingEvents", upcomingEvents);
        modelAndView.addObject("favouriteContacts", favouriteContactsProfiles);
        modelAndView.addObject("redirectMap", redirectMap);
        return modelAndView;
    }
}
