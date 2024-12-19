package com.example.productivity.controller;

import com.example.productivity.model.Event;
import com.example.productivity.model.EventAttendee;
import com.example.productivity.model.UserProfile;
import com.example.productivity.repository.EventAttendeesRepository;
import com.example.productivity.repository.EventRepository;
import com.example.productivity.repository.UserProfileRepository;
import com.example.productivity.service.CalendarService;
import com.example.productivity.service.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    NotificationsService notificationsService;

    @GetMapping("/event/create")
    public ModelAndView createEventPage() {return new ModelAndView("/calendar/createEvent");}

    @PostMapping("/event/create")
    public RedirectView createEvent(@RequestParam String title, @RequestParam String description, @RequestParam LocalDate date, @RequestParam LocalTime startTime, @RequestParam LocalTime endTime, @RequestParam(required = false) List<Long> contactIds){
        Long currentUserId = currentUser.getCurrentUser().getId();
        calendarService.createEventWithAttendees(title, description, date, startTime, endTime, currentUserId, contactIds);
        return new RedirectView("/calendar");
    }

    @PostMapping("/event/create/recur")
    public RedirectView createRecurringEvent(@RequestParam String title, @RequestParam String description, @RequestParam LocalDate date, @RequestParam LocalTime startTime,
                                             @RequestParam LocalTime endTime, @RequestParam(required = false) List<Long> contactIds, @RequestParam Integer recurCount){
        // New params:
        // recurCount - Amount of times for event to reoccur (saves db space)
        int day = date.getDayOfMonth();
        int month = date.getMonthValue();
        int year = LocalDate.now().getYear();
        Long currentUserId = currentUser.getCurrentUser().getId();

        for(int newYear = year;  newYear < recurCount + year; newYear++){

            LocalDate newDate = LocalDate.of(newYear,month,day);
            calendarService.createEventWithAttendees(title,description,newDate,startTime,endTime,currentUserId,contactIds);

        }
        return new RedirectView("/calendar");
    }

    @GetMapping("/events/{eventId}")
    public ModelAndView viewEvent(@PathVariable Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        return buildEventView(event);
    }

    @PatchMapping("/{eventId}/cancel")
    public ResponseEntity<Void> cancelEvent(@PathVariable Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        event.setIsCancelled(true);
        eventRepository.save(event);

        List<Long> attendeeIds = eventAttendeesRepository.findAttendeeIdsByEventId(eventId);
        for (Long attendeeId : attendeeIds) {
            notificationsService.eventIsCancelled(event.getUserId(), attendeeId, event);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/event/edit/{id}")
    public ModelAndView editEvent(@PathVariable Long id) {
        ModelAndView eventFormView = new ModelAndView("/calendar/updateEvent.html");
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        List<Long> attendeeIds = eventAttendeesRepository.findAttendeeIdsByEventId(id);
        List<EventAttendee> attendees = eventAttendeesRepository.findAttendeesByEventId(id);
        List<UserProfile> eventAttendees = userProfileRepository.findByUserIdIn(attendeeIds);

        Long userId = currentUser.getCurrentUser().getId();
        Boolean userIsEventOrganiser = userId.equals(event.getUserId());

        eventFormView.addObject("event", event);
        eventFormView.addObject("eventAttendees", eventAttendees);
        eventFormView.addObject("userIsEventOrganiser", userIsEventOrganiser);
        return eventFormView;
    }

    @PostMapping("/event/update/{id}")
    public ModelAndView updateEvent(@PathVariable Long id, @ModelAttribute Event updatedEvent, @RequestParam(required = false) List<Long> contactIds) {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        existingEvent.setTitle(updatedEvent.getTitle());
        existingEvent.setDescription(updatedEvent.getDescription());
        existingEvent.setDate(updatedEvent.getDate());
        existingEvent.setStartTime(updatedEvent.getStartTime());
        existingEvent.setEndTime(updatedEvent.getEndTime());
        eventRepository.save(existingEvent);
        if (contactIds != null && !contactIds.isEmpty()) {
            calendarService.addAttendeesToEvent(existingEvent, contactIds);
        }

        return buildEventView(existingEvent);
    }

    private ModelAndView buildEventView(Event event) {
        ModelAndView eventView = new ModelAndView("/calendar/showEvent.html");

        Long userId = currentUser.getCurrentUser().getId();
        Boolean userIsEventOrganiser = userId.equals(event.getUserId());
        Boolean userIsEventAttendee = eventAttendeesRepository.existsByAttendeeIdAndEventId(userId, event.getId());
        String userEventStatus = StringUtils.capitalize(eventAttendeesRepository.findUserEventStatus(event.getId(), userId));
        List<Long> attendeeIds = eventAttendeesRepository.findAttendeeIdsByEventId(event.getId());
        List<EventAttendee> attendees = eventAttendeesRepository.findAttendeesByEventId(event.getId());
        List<UserProfile> eventAttendees = userProfileRepository.findByUserIdIn(attendeeIds);
        Boolean eventIsCancelled = event.getIsCancelled();

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
        eventView.addObject("eventIsCancelled", eventIsCancelled);

        return eventView;
    }
}
