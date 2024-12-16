package com.example.productivity.controller;

import com.example.productivity.model.Notification;
import com.example.productivity.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class NotificationsController {
    @Autowired
    NotificationRepository notificationRepository;

    @PostMapping("/notifications/{id}")
    public RedirectView readNotification(@PathVariable Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setIsRead(true);
        notificationRepository.save(notification);
        return new RedirectView("/");
    }
}
