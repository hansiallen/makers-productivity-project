package com.example.productivity.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("auth0Id123", "testuser@example.com");
    }

    @Test
    void testConstructorAndGetters() {
        assertNotNull(user);
        assertEquals("auth0Id123", user.getAuth0Id());
        assertEquals("testuser@example.com", user.getEmail());
    }

    @Test
    void testSetters() {
        user.setAuth0Id("newAuth0Id");
        user.setEmail("newuser@example.com");

        assertEquals("newAuth0Id", user.getAuth0Id());
        assertEquals("newuser@example.com", user.getEmail());
    }

    @Test
    void testDefaultConstructor() {
        User userWithDefaultConstructor = new User();
        assertNotNull(userWithDefaultConstructor);
        assertNull(userWithDefaultConstructor.getAuth0Id());
        assertNull(userWithDefaultConstructor.getEmail());
    }
}
