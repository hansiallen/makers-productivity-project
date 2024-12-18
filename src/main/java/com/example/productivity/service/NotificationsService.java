package com.example.productivity.service;

import com.example.productivity.model.Event;
import com.example.productivity.model.Notification;
import com.example.productivity.repository.NotificationRepository;
import com.example.productivity.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationsService {
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    UserProfileRepository userProfileRepository;

    public void userAddsContactNotification(Long user1, Long user2) {
        String user2Name = userProfileRepository.findById(user2)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + user2))
                .getFirstName();
        Notification notification = new Notification();
        notification.setReceiverId(user1);
        notification.setSenderId(user2);
        notification.setType("contact");
        notification.setContent(user2Name + " was added as a contact");
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }

    public void userIsAddedAsContactNotification(Long user1, Long user2) {
        String user1Name = userProfileRepository.findById(user1)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + user1))
                .getFirstName();
        Notification notification = new Notification();
        notification.setReceiverId(user2);
        notification.setSenderId(user1);
        notification.setType("contact");
        notification.setContent(user1Name + " added you as a contact");
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }

    public void userIsInvitedToEvent(Long user1, Long user2, Event event) {
        String user1Name = userProfileRepository.findById(user1)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + user1))
                .getFirstName();
        Notification notification = new Notification();
        notification.setReceiverId(user2);
        notification.setSenderId(user1);
        notification.setEventId(event.getId());
        notification.setType("event");
        notification.setContent(user1Name + " invited you to the event: " + event.getTitle());
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }

    public void eventIsCancelled(Long user1, Long user2, Event event) {
        String user1Name = userProfileRepository.findById(user1)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + user1))
                .getFirstName();
        Notification notification = new Notification();
        notification.setReceiverId(user2);
        notification.setSenderId(user1);
        notification.setEventId(event.getId());
        notification.setType("event");
        notification.setContent(user1Name + " cancelled the event: " + event.getTitle());
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }

    public void eventIsUpdated(Long user1, Long user2, Event event) {
        String user1Name = userProfileRepository.findById(user1)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + user1))
                .getFirstName();
        Notification notification = new Notification();
        notification.setReceiverId(user2);
        notification.setSenderId(user1);
        notification.setEventId(event.getId());
        notification.setType("event");
        notification.setContent(user1Name + " updated the event: " + event.getTitle());
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }
}
