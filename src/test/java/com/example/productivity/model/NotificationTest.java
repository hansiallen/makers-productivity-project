package com.example.productivity.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {

    private Notification notification;

    @BeforeEach
    void setUp() {
        notification = new Notification(1L, 2L, "INFO", "Test Notification", false, 100L, LocalDateTime.now());
    }

    @Test
    void testConstructorWithEventId() {
        LocalDateTime now = LocalDateTime.now();
        Notification notification = new Notification(1L, 2L, "INFO", "Test content", false, 100L, now);

        assertNotNull(notification);
        assertEquals(1L, notification.getReceiverId());
        assertEquals(2L, notification.getSenderId());
        assertEquals("INFO", notification.getType());
        assertEquals("Test content", notification.getContent());
        assertFalse(notification.getIsRead());
        assertEquals(100L, notification.getEventId());
        assertEquals(now, notification.getCreatedAt());
    }

    @Test
    void testConstructorWithoutEventId() {
        LocalDateTime now = LocalDateTime.now();
        Notification notification = new Notification(1L, 2L, "INFO", "Test content", false, now);

        assertNotNull(notification);
        assertEquals(1L, notification.getReceiverId());
        assertEquals(2L, notification.getSenderId());
        assertEquals("INFO", notification.getType());
        assertEquals("Test content", notification.getContent());
        assertFalse(notification.getIsRead());
        assertNull(notification.getEventId());
        assertEquals(now, notification.getCreatedAt());
    }

    @Test
    void testGettersAndSetters() {
        notification.setReceiverId(3L);
        notification.setSenderId(4L);
        notification.setType("ERROR");
        notification.setContent("Error content");
        notification.setIsRead(true);
        notification.setEventId(200L);
        notification.setCreatedAt(LocalDateTime.now().minusDays(1));

        assertEquals(3L, notification.getReceiverId());
        assertEquals(4L, notification.getSenderId());
        assertEquals("ERROR", notification.getType());
        assertEquals("Error content", notification.getContent());
        assertTrue(notification.getIsRead());
        assertEquals(200L, notification.getEventId());
        assertTrue(notification.getCreatedAt().isBefore(LocalDateTime.now()));
    }
}
