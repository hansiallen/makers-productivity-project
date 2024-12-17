package com.example.productivity.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContactTest {

    private Contact contact;

    @BeforeEach
    void setUp() {
        contact = new Contact(1L, 2L, true);
    }

    @Test
    void testConstructorAndGetters() {
        assertNotNull(contact);
        assertEquals(1L, contact.getUserId1());
        assertEquals(2L, contact.getUserId2());
        assertTrue(contact.getIsFavourite());
    }

    @Test
    void testSetters() {
        contact.setUserId1(3L);
        contact.setUserId2(4L);
        contact.setIsFavourite(false);

        assertEquals(3L, contact.getUserId1());
        assertEquals(4L, contact.getUserId2());
        assertFalse(contact.getIsFavourite());
    }

    @Test
    void testDefaultConstructor() {
        Contact defaultContact = new Contact();
        assertNotNull(defaultContact);
        assertNull(defaultContact.getUserId1());
        assertNull(defaultContact.getUserId2());
        assertFalse(defaultContact.getIsFavourite());
    }

    @Test
    void testConstructorWithTwoArgs() {
        Contact contact2 = new Contact(1L, 2L);
        assertNotNull(contact2);
        assertEquals(1L, contact2.getUserId1());
        assertEquals(2L, contact2.getUserId2());
        assertFalse(contact2.getIsFavourite());
    }
}
