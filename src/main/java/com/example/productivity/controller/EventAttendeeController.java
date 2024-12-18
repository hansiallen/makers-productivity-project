package com.example.productivity.controller;

import com.example.productivity.model.Event;
import com.example.productivity.model.EventAttendee;
import com.example.productivity.repository.EventAttendeesRepository;
import com.example.productivity.repository.EventRepository;
import com.example.productivity.service.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EventAttendeeController {
    @Autowired
    EventAttendeesRepository eventAttendeesRepository;
    @Autowired
    NotificationsService notificationsService;
    @Autowired
    EventRepository eventRepository;

    @PatchMapping("/{eventId}/{attendeeId}/status")
    public ResponseEntity<Void> setEventStatusForAttendee(@PathVariable Long eventId, @PathVariable Long attendeeId, @RequestBody Map<String, String> statusRequest) {
        EventAttendee eventAttendee = eventAttendeesRepository
                .findByEventIdAndAttendeeId(eventId, attendeeId)
                .orElseThrow(() -> new RuntimeException("Attendee not found for the event"));
        String status = statusRequest.get("status");
        eventAttendee.setAttendingStatus(status);
        eventAttendeesRepository.save(eventAttendee);

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));;

        notificationsService.userRespondsToEventInvitation(event.getUserId(), attendeeId, event, status);

        return ResponseEntity.ok().build();
    }
}
