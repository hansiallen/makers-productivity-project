package com.example.productivity.model;

import jakarta.persistence.Entity;

import java.util.Date;

@Entity
public class CalendarDay {

    private Date date;

    CalendarDay(){
    }
    CalendarDay(Date date){
        this.date  =date;

    }
}
