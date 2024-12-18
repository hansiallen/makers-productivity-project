package com.example.productivity.repository;

import com.example.productivity.model.EventAttendee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EventAttendeesRepository extends CrudRepository<EventAttendee,Long> {
    @Query(value = "SELECT attendee_id FROM event_attendees WHERE event_id = :eventId;", nativeQuery = true)
    List<Long> findAttendeeIdsByEventId(Long eventId);

    List<EventAttendee> findAttendeesByEventId(Long eventId);

    Optional<EventAttendee> findByEventIdAndAttendeeId(Long eventId, Long attendeeId);

    @Query(value = """
        SELECT attending_status
        FROM event_attendees
        WHERE event_id = :eventId AND attendee_id = :userId
        LIMIT 1;
    """, nativeQuery = true)
    String findUserEventStatus(@Param("eventId") Long eventId, @Param("userId") Long userId);

//    @Query(value = "SELECT EXISTS(SELECT 1 FROM event_attendees WHERE event_id = 8 AND attendee_id = 1);", nativeQuery = true)
//    Boolean userIsEventAttendee(Long userId, Long eventId);
    boolean existsByAttendeeIdAndEventId(Long userId, Long eventId);
}
