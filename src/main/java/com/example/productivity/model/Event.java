package com.example.productivity.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "EVENTS")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String title;
    private String description;
    private Long userId;

    public Event() {
    }

    public Event(LocalDate date, LocalTime startTime, LocalTime endTime, String title, String description, Long userId) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.description = description;
        this.userId = userId;
    }

    private Long getId() {
        return this.id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    private LocalDate getDate() {
        return this.date;
    }

    private void setDate(LocalDate date) {
        this.date = date;
    }

    private LocalTime getStartTime() {
        return this.startTime;
    }

    private void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    private LocalTime getEndTime() {
        return this.endTime;
    }

    private void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    private String getTitle() {
        return this.title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    private String getDescription() {
        return this.description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    private Long getUserId() {
        return this.userId;
    }

    private void setUserId(Long userId) {
        this.userId = userId;
    }
}
