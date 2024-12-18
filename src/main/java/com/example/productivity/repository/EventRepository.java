package com.example.productivity.repository;

import com.example.productivity.model.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends CrudRepository<Event, Long> {
    @Query(value = """
        SELECT *
        FROM events e
        WHERE ((e.date > CURRENT_DATE)
           OR (e.date = CURRENT_DATE AND e.start_time > CURRENT_TIME))
           AND e.user_id = :userId
        ORDER BY e.date ASC, e.start_time ASC
        LIMIT :limit
    """, nativeQuery = true)
    List<Event> findNextUpcomingEvents(int limit, Long userId);

    @Query(value = """
        SELECT e.id, e.date, e.start_time, e.end_time, e.title, e.description, e.user_id, e.is_cancelled
        FROM events e, event_attendees a
        WHERE (e.date >= :earliest)
           and (e.date < :latest) and (:userId = e.user_id) or (a.attendee_id = :userId and a.event_id = e.id)
        ORDER BY e.date ASC, e.start_time ASC
    """, nativeQuery = true)
    List<Event> findEventsInTimePeriodForUser(Long userId, LocalDate earliest, LocalDate latest);
}
