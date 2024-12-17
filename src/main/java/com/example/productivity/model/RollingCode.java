package com.example.productivity.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Timestamp;

@Entity
@Table(name = "ROLLING_CODES")
public class RollingCode {
    @Id
    private Long code;
    private Long userId;
    private Timestamp expiryTime;

    public RollingCode() {
    }

    public RollingCode(Long code, Long userId, Timestamp expiryTime) {
        this.code = code;
        this.userId = userId;
        this.expiryTime = expiryTime;
    }

    public Long getCode() { return this.code; }
    public void setCode(Long code) { this.code = code; }

    public Long getUserId() { return this.userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Timestamp getExpiryTime() { return this.expiryTime; }
    public void setExpiryTime(Timestamp expiryTime) { this.expiryTime = expiryTime; }
}
