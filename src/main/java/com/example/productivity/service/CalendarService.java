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
        Event newEvent = new Event(date, startTime, endTime, title, description, userId, false);
        eventRepository.save(newEvent);
        if (contactIds != null && !contactIds.isEmpty()) {
            for (Long contactId : contactIds) {
                EventAttendee eventAttendee = new EventAttendee(newEvent.getId(), contactId, "pending");
                eventAttendeesRepository.save(eventAttendee);
                notificationsService.userIsInvitedToEvent(userId, contactId, newEvent);
            }
        }
    }

    public void addAttendeesToEvent(Event event, List<Long> contactIds) {
        if (contactIds != null && !contactIds.isEmpty()) {
            for (Long contactId : contactIds) {
                boolean attendeeExists = eventAttendeesRepository.existsByAttendeeIdAndEventId(contactId, event.getId());

                if (!attendeeExists) {
                    EventAttendee eventAttendee = new EventAttendee(event.getId(), contactId, "pending");
                    eventAttendeesRepository.save(eventAttendee);

                    notificationsService.userIsInvitedToEvent(event.getUserId(), contactId, event);
                } else {
                    EventAttendee attendee = eventAttendeesRepository.findByEventIdAndAttendeeId(event.getId(), contactId)
                            .orElseThrow(() -> new RuntimeException("Attendee not found"));
                    if (attendee.getAttendingStatus().equals("removed")) {
                        attendee.setAttendingStatus("pending");
                        eventAttendeesRepository.save(attendee);
                        notificationsService.userIsInvitedToEvent(event.getUserId(), contactId, event);
                    } else {
                        notificationsService.eventIsUpdated(event.getUserId(), contactId, event);
                    }
                }
            }
        }
    }
}
