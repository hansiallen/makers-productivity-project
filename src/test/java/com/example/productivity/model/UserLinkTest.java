package com.example.productivity.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserLinkTest {

    private UserLink userLink;

    @BeforeEach
    void setUp() {
        userLink = new UserLink(1L, "GitHub", "https://github.com");
    }

    @Test
    void testConstructorAndGetters() {
        assertNotNull(userLink);
        assertEquals(1L, userLink.getUserId());
        assertEquals("GitHub", userLink.getWebsiteLinkName());
        assertEquals("https://github.com", userLink.getWebsiteLinkUrl());
    }

    @Test
    void testSetters() {
        userLink.setUserId(2L);
        userLink.setWebsiteLinkName("LinkedIn");
        userLink.setWebsiteLinkUrl("https://linkedin.com");

        assertEquals(2L, userLink.getUserId());
        assertEquals("LinkedIn", userLink.getWebsiteLinkName());
        assertEquals("https://linkedin.com", userLink.getWebsiteLinkUrl());
    }

    @Test
    void testDefaultConstructor() {
        UserLink defaultUserLink = new UserLink();
        assertNotNull(defaultUserLink);
        assertNull(defaultUserLink.getWebsiteLinkName());
        assertNull(defaultUserLink.getWebsiteLinkUrl());
        assertNull(defaultUserLink.getUserId());
    }
}
