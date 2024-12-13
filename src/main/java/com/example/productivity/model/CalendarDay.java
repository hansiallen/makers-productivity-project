package com.example.productivity.model;


import java.time.LocalDate;
import java.util.*;

public class CalendarDay {

    private LocalDate date;
    private List<String> events;
    private boolean greyedOut;
    CalendarDay(){
    }
    public CalendarDay(LocalDate date,boolean greyedOut){
        this.date  = date;
        this.greyedOut = greyedOut;
        this.events = new LinkedList<>();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public int getDay() {
        return this.date.getDayOfMonth();
    }
    public List<String> getEvents(){
        this.events.add("item");
        this.events.add("item");
        return events;
    }
    public void addEvent(String Event){
        this.events.add(Event);
    }

    public void setGreyedOut(boolean greyedOut) {
        this.greyedOut = greyedOut;
    }

    public boolean isGreyedOut() {
        return greyedOut;
    }

    public String getURL(){
        return "/calendar/day/"+this.date.getDayOfMonth()+"_"+this.date.getMonthValue()+"_"+this.date.getYear();
    }
}
