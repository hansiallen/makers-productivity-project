package com.example.productivity.controller;


import com.example.productivity.model.CalendarDay;
import com.example.productivity.model.Event;
import com.example.productivity.repository.EventRepository;
import com.example.productivity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CalendarController {
    @Autowired
    EventRepository eventRepository;
    @Autowired
    CurrentUserController currentUser;

    @GetMapping("/calendar")
    public RedirectView calendarPageRedirect() {
        LocalDate currentTime =LocalDate.now();
        return new RedirectView("/calendar/month/"+currentTime.getMonth().getValue()+"_"+currentTime.getYear());
    }

    @GetMapping("/calendar/day/{day}_{month}_{year}")
    public ModelAndView dayPage(@PathVariable Integer day, @PathVariable Integer month, @PathVariable Integer year) {
        ModelAndView model = new ModelAndView("calendar/day");
        LocalDate currentTime =LocalDate.of(year,month,day);
        return model;
    }

    @GetMapping("/calendar/month/{month}_{year}")
    public ModelAndView calendarPage(@PathVariable Integer month, @PathVariable Integer year) {
        ModelAndView model = new ModelAndView("calendar/grid");
        LocalDate currentTime =LocalDate.of(year,month,1);
        LocalDate nextMonth = currentTime.plusMonths(1);
        LocalDate lastMonth = currentTime.plusMonths(-1);
        model.addObject("nextMonth", "month/"+nextMonth.getMonth().getValue()+"_"+nextMonth.getYear());
        model.addObject("lastMonth","month/"+lastMonth.getMonth().getValue()+"_"+lastMonth.getYear());


        //adds white spaces so you start on the correct day of the month
        List<CalendarDay> l = CreateArrayOfDates(lastMonth,true);
        l= l.reversed().subList(0,currentTime.withDayOfMonth(1).getDayOfWeek().getValue()-1).reversed();//gets greyed out days

        //main list
        l.addAll(CreateArrayOfDates(currentTime,false));//gets non greyed out days

        //adds dates of the next month
        List<CalendarDay> m = CreateArrayOfDates(nextMonth,true);
        m = m.subList(0,7-(currentTime.withDayOfMonth(currentTime.getMonth().length(currentTime.isLeapYear())).getDayOfWeek().getValue()-1));
        if(m.size()!=7){
            l.addAll(m);
        }

        model.addObject("month", currentTime.getMonth().name());
        model.addObject("days", l);
        return model;
    }

    private ArrayList<CalendarDay> CreateArrayOfDates(LocalDate date,Boolean greyedOut){
        List<Event> events= eventRepository.findEventsInTimePeriodForUser(currentUser.getCurrentUser().getId(),date,date.plusMonths(1));
        ArrayList<CalendarDay> days= new ArrayList<>();
        for (int day=1;day< date.getMonth().length(date.isLeapYear());day++){
            CalendarDay calendarDay = new CalendarDay(LocalDate.of(date.getYear(),date.getMonthValue(),day),greyedOut);
            for (Event event : events){
                if (event.getDate().toString().equals(calendarDay.getDate().toString())){
                    calendarDay.addEvent(event.getTitle());
                }
            }
            days.add(calendarDay);
        }
        return days;
    }
}
