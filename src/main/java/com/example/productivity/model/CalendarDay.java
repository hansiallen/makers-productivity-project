package com.example.productivity.model;


import java.time.LocalDate;

public class CalendarDay {

    private LocalDate date;

    CalendarDay(){
    }
    public CalendarDay(LocalDate date){
        this.date  = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
