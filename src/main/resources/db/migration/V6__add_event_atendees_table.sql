DROP TABLE IF EXISTS event_attendees;

CREATE TABLE event_attendees (
    id BIGSERIAL PRIMARY KEY,
    event_id BIGINT,
          CONSTRAINT fk_event
            FOREIGN KEY (event_id)
            REFERENCES events(id)
            ON DELETE CASCADE,
    attendee_id BIGINT,
      CONSTRAINT fk_attendee
        FOREIGN KEY (attendee_id)
        REFERENCES user_profiles(id)
        ON DELETE CASCADE,
   attending_status varchar(63)
);
