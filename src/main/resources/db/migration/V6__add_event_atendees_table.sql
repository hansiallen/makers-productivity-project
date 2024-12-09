DROP TABLE IF EXISTS event_attendees;

CREATE TABLE event_attendee (
    id BIGSERIAL PRIMARY KEY,
    event_id BIGSERIAL,
          CONSTRAINT fk_event
            FOREIGN KEY (event_id)
            REFERENCES events(id)
            ON DELETE CASCADE,
    attendee_id BIGSERIAL,
      CONSTRAINT fk_attendee
        FOREIGN KEY (attendee_id)
        REFERENCES user_profiles(id)
        ON DELETE CASCADE,
   attending_status varchar(63)
);
