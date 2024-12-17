package com.example.productivity.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventAttendeeTest {

    private EventAttendee eventAttendee;

    @BeforeEach
    void setUp() {
        eventAttendee = new EventAttendee(1L, 100L, "pending");
    }

    @Test
    void testConstructorAndGetters() {
        assertNotNull(eventAttendee);
        assertEquals(1L, eventAttendee.getEventId());
        assertEquals(100L, eventAttendee.getAttendeeId());
        assertEquals("pending", eventAttendee.getAttendingStatus());
    }

    @Test
    void testSetters() {
        eventAttendee.setEventId(2L);
        eventAttendee.setAttendeeId(200L);
        eventAttendee.setAttendingStatus("yes");

        assertEquals(2L, eventAttendee.getEventId());
        assertEquals(200L, eventAttendee.getAttendeeId());
        assertEquals("yes", eventAttendee.getAttendingStatus());
    }

    @Test
    void testDefaultConstructor() {
        EventAttendee defaultEventAttendee = new EventAttendee();

        assertNull(defaultEventAttendee.getEventId());
        assertNull(defaultEventAttendee.getAttendeeId());
        assertNull(defaultEventAttendee.getAttendingStatus());
    }

    @Test
    void testSettersAndGettersAfterDefaults() {
        EventAttendee defaultEventAttendee = new EventAttendee();
        defaultEventAttendee.setEventId(3L);
        defaultEventAttendee.setAttendeeId(300L);
        defaultEventAttendee.setAttendingStatus("pending");

        assertEquals(3L, defaultEventAttendee.getEventId());
        assertEquals(300L, defaultEventAttendee.getAttendeeId());
        assertEquals("pending", defaultEventAttendee.getAttendingStatus());
    }

    @Test
    void testNullValues() {
        eventAttendee.setEventId(null);
        eventAttendee.setAttendeeId(null);
        eventAttendee.setAttendingStatus(null);

        assertNull(eventAttendee.getEventId());
        assertNull(eventAttendee.getAttendeeId());
        assertNull(eventAttendee.getAttendingStatus());
    }
}
