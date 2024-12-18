package com.example.productivity.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomFieldTest {

    private CustomField customField;

    @BeforeEach
    void setUp() {
        customField = new CustomField(1L, "key1", "content1");
    }

    @Test
    void testConstructorAndGetters() {
        assertNotNull(customField);
        assertEquals(1L, customField.getUserId());
        assertEquals("key1", customField.getCustomInfoKey());
        assertEquals("content1", customField.getCustomInfoContent());
    }

    @Test
    void testSetters() {
        customField.setUserId(2L);
        customField.setCustomInfoKey("key2");
        customField.setCustomInfoContent("content2");

        assertEquals(2L, customField.getUserId());
        assertEquals("key2", customField.getCustomInfoKey());
        assertEquals("content2", customField.getCustomInfoContent());
    }

    @Test
    void testDefaultConstructor() {
        CustomField defaultCustomField = new CustomField();

        assertNull(defaultCustomField.getCustomInfoKey());
        assertNull(defaultCustomField.getCustomInfoContent());
        assertNull(defaultCustomField.getUserId());
    }

    @Test
    void testSettersAndGettersAfterDefaults() {
        CustomField defaultCustomField = new CustomField();
        defaultCustomField.setUserId(3L);
        defaultCustomField.setCustomInfoKey("key3");
        defaultCustomField.setCustomInfoContent("content3");

        assertEquals(3L, defaultCustomField.getUserId());
        assertEquals("key3", defaultCustomField.getCustomInfoKey());
        assertEquals("content3", defaultCustomField.getCustomInfoContent());
    }
}
