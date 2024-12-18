package com.example.productivity.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserProfileTest {

    private UserProfile userProfile;
    private User user;

    @BeforeEach
    void setUp() {
        userProfile = new UserProfile(
                1L,
                "John",
                "Doe",
                "Smith",
                "http://example.com/photo.jpg",
                "Johnny",
                "johnny.doe@example.com",
                "123-456-7890",
                LocalDate.of(1990, 1, 1)
        );
    }

    @Test
    void testConstructorAndGetters() {
        assertNotNull(userProfile);
        assertEquals(1L, userProfile.getUserId());
        assertEquals("John", userProfile.getFirstName());
        assertEquals("Smith", userProfile.getLastName());
        assertEquals("Johnny", userProfile.getPreferredName());
        assertEquals("http://example.com/photo.jpg", userProfile.getProfilePhotoUrl());
    }

    @Test
    void testSetters() {
        userProfile.setFirstName("Jane");
        userProfile.setLastName("Doe");
        userProfile.setMiddleName("A.");
        userProfile.setPreferredName("Janey");
        userProfile.setProfilePhotoUrl("http://example.com/jane.jpg");

        assertEquals("Jane", userProfile.getFirstName());
        assertEquals("Doe", userProfile.getLastName());
        assertEquals("A.", userProfile.getMiddleName());
        assertEquals("Janey", userProfile.getPreferredName());
        assertEquals("http://example.com/jane.jpg", userProfile.getProfilePhotoUrl());
    }

    @Test
    void testDefaultConstructor() {
        UserProfile defaultUserProfile = new UserProfile();
        assertNotNull(defaultUserProfile);
        assertNull(defaultUserProfile.getFirstName());
        assertNull(defaultUserProfile.getLastName());
        assertNull(defaultUserProfile.getPreferredName());
        assertNull(defaultUserProfile.getProfilePhotoUrl());
    }
}