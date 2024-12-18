package com.example.productivity.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    private Event event;

    @BeforeEach
    void setUp() {
        event = new Event(LocalDate.of(2024, 12, 17), LocalTime.of(9, 0), LocalTime.of(10, 0), "Team Meeting", "Discuss project updates", 1L, false);
    }

    @Test
    void testConstructorAndGetters() {
        assertNotNull(event);
        assertEquals(LocalDate.of(2024, 12, 17), event.getDate());
        assertEquals(LocalTime.of(9, 0), event.getStartTime());
        assertEquals(LocalTime.of(10, 0), event.getEndTime());
        assertEquals("Team Meeting", event.getTitle());
        assertEquals("Discuss project updates", event.getDescription());
        assertEquals(1L, event.getUserId());
        assertFalse(event.getIsCancelled());
    }

    @Test
    void testSetters() {
        event.setDate(LocalDate.of(2024, 12, 18));
        event.setStartTime(LocalTime.of(10, 0));
        event.setEndTime(LocalTime.of(11, 0));
        event.setTitle("Client Meeting");
        event.setDescription("Discuss requirements");
        event.setUserId(2L);
        event.setIsCancelled(false);

        assertEquals(LocalDate.of(2024, 12, 18), event.getDate());
        assertEquals(LocalTime.of(10, 0), event.getStartTime());
        assertEquals(LocalTime.of(11, 0), event.getEndTime());
        assertEquals("Client Meeting", event.getTitle());
        assertEquals("Discuss requirements", event.getDescription());
        assertEquals(2L, event.getUserId());
        assertFalse(event.getIsCancelled());
    }

    @Test
    void testDefaultConstructor() {
        Event defaultEvent = new Event();

        assertNull(defaultEvent.getDate());
        assertNull(defaultEvent.getStartTime());
        assertNull(defaultEvent.getEndTime());
        assertNull(defaultEvent.getTitle());
        assertNull(defaultEvent.getDescription());
        assertNull(defaultEvent.getUserId());
        assertFalse(defaultEvent.getIsCancelled());
    }

    @Test
    void testSettersAndGettersAfterDefaults() {
        Event defaultEvent = new Event();
        defaultEvent.setDate(LocalDate.of(2024, 12, 19));
        defaultEvent.setStartTime(LocalTime.of(15, 0));
        defaultEvent.setEndTime(LocalTime.of(16, 0));
        defaultEvent.setTitle("Team Sync");
        defaultEvent.setDescription("Monthly sync-up meeting");
        defaultEvent.setUserId(3L);
        defaultEvent.setIsCancelled(true);

        assertEquals(LocalDate.of(2024, 12, 19), defaultEvent.getDate());
        assertEquals(LocalTime.of(15, 0), defaultEvent.getStartTime());
        assertEquals(LocalTime.of(16, 0), defaultEvent.getEndTime());
        assertEquals("Team Sync", defaultEvent.getTitle());
        assertEquals("Monthly sync-up meeting", defaultEvent.getDescription());
        assertEquals(3L, defaultEvent.getUserId());
        assertTrue(defaultEvent.getIsCancelled());
    }

    @Test
    void testEventTimes() {
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(10, 0);

        event.setStartTime(startTime);
        event.setEndTime(endTime);

        assertTrue(event.getStartTime().isBefore(event.getEndTime()), "Start time should be before end time.");
    }
}
