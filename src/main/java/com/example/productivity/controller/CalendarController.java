package com.example.productivity.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

@RestController
public class CalendarController {

    @GetMapping("/calendar")
    public ModelAndView calendarPageRedirect() {
        ModelAndView model = new ModelAndView("calendar/grid");
        LocalDate currentTime =LocalDate.now();

        //adds white spaces so you start on the correct day of the month
        model.addObject("startDay", currentTime.withDayOfMonth(1).getDayOfWeek().getValue()-1);

        model.addObject("month", currentTime.getMonth().name());
        model.addObject("days", currentTime.getMonth().length(currentTime.isLeapYear()));
        return model;
    }


    @GetMapping("/calendar/{month}_{year}")
    public ModelAndView calendarPage(@PathVariable Integer month, @PathVariable Integer year) {
        ModelAndView model = new ModelAndView("calendar/grid");
        LocalDate currentTime =LocalDate.of(year,month,1);

        //adds white spaces so you start on the correct day of the month
        model.addObject("startDay", currentTime.withDayOfMonth(1).getDayOfWeek().getValue()-1);

        model.addObject("month", currentTime.getMonth().name());
        model.addObject("days", currentTime.getMonth().length(currentTime.isLeapYear()));
        return model;
    }
}
