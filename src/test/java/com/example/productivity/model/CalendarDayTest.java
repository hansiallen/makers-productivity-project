package com.example.productivity.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalendarDayTest {

    private CalendarDay calendarDay;

    @BeforeEach
    void setUp() {
        calendarDay = new CalendarDay(LocalDate.of(2024, 12, 17), false);
    }

    @Test
    void testConstructorAndGetters() {
        assertNotNull(calendarDay);
        assertEquals(LocalDate.of(2024, 12, 17), calendarDay.getDate());
        assertEquals(17, calendarDay.getDay());
        assertFalse(calendarDay.isGreyedOut());
    }

    @Test
    void testSetters() {
        calendarDay.setDate(LocalDate.of(2024, 12, 18));
        calendarDay.setGreyedOut(true);

        assertEquals(LocalDate.of(2024, 12, 18), calendarDay.getDate());
        assertTrue(calendarDay.isGreyedOut());
    }

    @Test
    void testAddEvent() {
        calendarDay.addEvent("Meeting with team");
        calendarDay.addEvent("Doctor's appointment");

        List<String> events = calendarDay.getEvents();

        assertNotNull(events);
        assertEquals(4, events.size());
        assertTrue(events.contains("Meeting with team"));
        assertTrue(events.contains("Doctor's appointment"));
    }

    @Test
    void testGetEvents() {
        calendarDay.addEvent("Event 1");
        calendarDay.addEvent("Event 2");

        List<String> events = calendarDay.getEvents();

        assertNotNull(events);
        assertEquals(4, events.size());
        assertTrue(events.contains("Event 1"));
        assertTrue(events.contains("Event 2"));
    }

    @Test
    void testGreyedOut() {
        calendarDay.setGreyedOut(true);
        assertTrue(calendarDay.isGreyedOut());

        calendarDay.setGreyedOut(false);
        assertFalse(calendarDay.isGreyedOut());
    }
}
