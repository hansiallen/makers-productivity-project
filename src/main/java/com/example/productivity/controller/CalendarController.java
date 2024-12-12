package com.example.productivity.controller;


import net.datafaker.providers.base.Bool;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;

@RestController
public class CalendarController {

    @GetMapping("/calendar")
    public RedirectView calendarPageRedirect() {
        ModelAndView model = new ModelAndView("calendar/grid");
        LocalDate currentTime =LocalDate.now();
        return new RedirectView("/calendar/"+currentTime.getMonth().getValue()+"_"+currentTime.getYear());
    }
    @GetMapping("/calendar/{day}_{month}_{year}")
    public ModelAndView dayPage(@PathVariable Integer day, @PathVariable Integer month, @PathVariable Integer year) {
        ModelAndView model = new ModelAndView("calendar/day");
        LocalDate currentTime =LocalDate.of(year,month,1);
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
        model.addObject("nextMonth", changeDate(month,year,true));
        model.addObject("lastMonth", changeDate(month,year,false));


        //adds white spaces so you start on the correct day of the month
        model.addObject("startDay", currentTime.withDayOfMonth(1).getDayOfWeek().getValue()-1);

        model.addObject("month", currentTime.getMonth().name());
        model.addObject("days", currentTime.getMonth().length(currentTime.isLeapYear()));
        return model;
    }

    private String changeDate(Integer month, Integer year, Boolean increase){
        if (increase && month == 12){
            return "1_"+(year+1);
        }
        else if(!increase && month == 1){
            return "12_"+(year-1);
        }
        if (increase){month++;}
        else{month--;}
        return month+"_"+year;
    }
}
