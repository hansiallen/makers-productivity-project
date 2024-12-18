package com.example.productivity.controller;

import com.example.productivity.model.Event;
import com.example.productivity.model.EventAttendee;
import com.example.productivity.model.UserProfile;
import com.example.productivity.repository.EventAttendeesRepository;
import com.example.productivity.repository.EventRepository;
import com.example.productivity.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

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
