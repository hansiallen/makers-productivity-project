package com.example.productivity.repository;

import com.example.productivity.model.Notification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
    List<Notification> findByReceiverIdAndIsReadFalse(Long userId);
}
