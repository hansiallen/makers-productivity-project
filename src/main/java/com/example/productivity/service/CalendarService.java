package com.example.productivity.service;

import com.example.productivity.model.Event;
import com.example.productivity.model.EventAttendee;
import com.example.productivity.repository.EventAttendeesRepository;
import com.example.productivity.repository.EventRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class CalendarService {
    @Autowired
    EventAttendeesRepository eventAttendeesRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    NotificationsService notificationsService;

    @Transactional
    public void createEventWithAttendees(String title, String description, LocalDate date, LocalTime startTime, LocalTime endTime, Long userId, List<Long> contactIds) {
        Event newEvent = new Event(date, startTime, endTime, title, description, userId);
        eventRepository.save(newEvent);
        if (contactIds != null && !contactIds.isEmpty()) {
            for (Long contactId : contactIds) {
                EventAttendee eventAttendee = new EventAttendee(newEvent.getId(), contactId, "pending");
                eventAttendeesRepository.save(eventAttendee);
                notificationsService.userIsInvitedToEvent(userId, contactId, newEvent);
            }
        }
    }
}
