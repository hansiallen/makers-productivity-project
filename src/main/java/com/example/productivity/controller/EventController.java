package com.example.productivity.controller;

import com.example.productivity.model.Event;
import com.example.productivity.model.EventAttendee;
import com.example.productivity.model.UserProfile;
import com.example.productivity.repository.EventAttendeesRepository;
import com.example.productivity.repository.EventRepository;
import com.example.productivity.repository.UserProfileRepository;
import com.example.productivity.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class EventController {
    @Autowired
    EventRepository eventRepository;
    @Autowired
    EventAttendeesRepository eventAttendeesRepository;
    @Autowired
    CurrentUserController currentUser;
    @Autowired
    UserProfileRepository userProfileRepository;
    @Autowired
    CalendarService calendarService;

    @GetMapping("/event/create")
    public ModelAndView createEventPage() {return new ModelAndView("/calendar/createEvent");}

    @PostMapping("/event/create")
    public RedirectView createEvent(@RequestParam String title, @RequestParam String description, @RequestParam LocalDate date, @RequestParam LocalTime startTime, @RequestParam LocalTime endTime, @RequestParam(required = false) List<Long> contactIds){
        Long currentUserId = currentUser.getCurrentUser().getId();
        calendarService.createEventWithAttendees(title, description, date, startTime, endTime, currentUserId, contactIds);
        return new RedirectView("/calendar");
    }

    @GetMapping("/events/{eventId}")
    public ModelAndView viewEvent(@PathVariable Long eventId) {
        ModelAndView eventView = new ModelAndView("/calendar/showEvent.html");
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        Long userId = currentUser.getCurrentUser().getId();
        Boolean userIsEventOrganiser = userId.equals(event.getUserId());
        Boolean userIsEventAttendee = eventAttendeesRepository.existsByAttendeeIdAndEventId(userId, eventId);
        String userEventStatus = StringUtils.capitalize(eventAttendeesRepository.findUserEventStatus(eventId, userId));
        List<Long> attendeeIds = eventAttendeesRepository.findAttendeeIdsByEventId(eventId);
        List<EventAttendee> attendees = eventAttendeesRepository.findAttendeesByEventId(eventId);
        List<UserProfile> eventAttendees = userProfileRepository.findByUserIdIn(attendeeIds);

        Map<Long, String> attendeeEventStatuses = new HashMap<>();
        for (EventAttendee attendee : attendees) {
            attendeeEventStatuses.put(attendee.getAttendeeId(), StringUtils.capitalize(attendee.getAttendingStatus()));
        }

        eventView.addObject("event", event);
        eventView.addObject("userIsEventAttendee", userIsEventAttendee);
        eventView.addObject("eventAttendees", eventAttendees);
        eventView.addObject("userIsEventOrganiser", userIsEventOrganiser);
        eventView.addObject("userEventStatus", userEventStatus);
        eventView.addObject("attendeeEventStatuses", attendeeEventStatuses);
        eventView.addObject("currentUserId", userId);
        return eventView;
    }
}