package com.example.productivity.repository;

import com.example.productivity.model.EventAttendee;
import org.springframework.data.repository.CrudRepository;

public interface EventAttendeesRepository extends CrudRepository<EventAttendee,Long> {
}
