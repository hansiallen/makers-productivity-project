package com.example.productivity.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class RollingCodeTest {

    private RollingCode rollingCode;

    @BeforeEach
    void setUp() {
        Instant instant = Instant.now();
        Timestamp expiryTime = Timestamp.from(instant);
        rollingCode = new RollingCode(12345L, 67890L, expiryTime);
    }

    @Test
    void testConstructorWithAllFields() {
        Instant instant = Instant.now();
        Timestamp expiryTime = Timestamp.from(instant);
        RollingCode rollingCode = new RollingCode(12345L, 67890L, expiryTime);

        assertNotNull(rollingCode);
        assertEquals(12345L, rollingCode.getCode());
        assertEquals(67890L, rollingCode.getUserId());
        assertEquals(expiryTime, rollingCode.getExpiryTime());
    }

    @Test
    void testGettersAndSetters() {
        Instant instant = Instant.now();
        Timestamp expiryTime = Timestamp.from(instant);

        rollingCode.setCode(54321L);
        rollingCode.setUserId(98765L);
        rollingCode.setExpiryTime(expiryTime);

        assertEquals(54321L, rollingCode.getCode());
        assertEquals(98765L, rollingCode.getUserId());
        assertEquals(expiryTime, rollingCode.getExpiryTime());
    }

    @Test
    void testCodeGetterSetter() {
        rollingCode.setCode(55555L);
        assertEquals(55555L, rollingCode.getCode());
    }

    @Test
    void testUserIdGetterSetter() {
        rollingCode.setUserId(123456L);
        assertEquals(123456L, rollingCode.getUserId());
    }

    @Test
    void testExpiryTimeGetterSetter() {
        Instant instant = Instant.now().plusSeconds(10000);
        Timestamp newExpiryTime = Timestamp.from(instant);
        rollingCode.setExpiryTime(newExpiryTime);

        assertEquals(newExpiryTime, rollingCode.getExpiryTime());
    }
}
