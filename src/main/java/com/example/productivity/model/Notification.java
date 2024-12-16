package com.example.productivity.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "NOTIFICATIONS")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long receiverId;
    private Long senderId;
    private String type;
    private String content;
    private boolean isRead;
    private Long eventId;
    private LocalDateTime createdAt;

    public Notification() {};

    public Notification(Long receiverId, Long senderId, String type, String content, boolean isRead, Long eventId, LocalDateTime createdAt) {
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.type = type;
        this.content = content;
        this.isRead = isRead;
        this.eventId = eventId;
        this.createdAt = createdAt;
    }

    public Notification(Long receiverId, Long senderId, String type, String content, boolean isRead, LocalDateTime createdAt) {
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.type = type;
        this.content = content;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }

    public Long getId() { return this.id; }

    public Long getReceiverId() { return this.receiverId; }
    public void setReceiverId(Long receiverId) { this.receiverId = receiverId; }

    public Long getSenderId() { return this.senderId; }
    public void setSenderId(Long senderId) { this.senderId = senderId; }

    public String getType() { return this.type; }
    public void setType(String type) { this.type = type; }

    public String getContent() { return this.content; }
    public void setContent(String content) { this.content = content; }

    public boolean getIsRead() { return this.isRead; }
    public void setIsRead(boolean isRead) { this.isRead = isRead; }

    public Long getEventId() { return this.eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }

    public LocalDateTime getCreatedAt() { return this.createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
