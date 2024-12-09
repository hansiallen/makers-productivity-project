DROP TABLE IF EXISTS event_attendees;

CREATE TABLE event_attendees (
    id BIGSERIAL PRIMARY KEY,
    event_id BIGSERIAL,
          CONSTRAINT fk_event_id
            FOREIGN KEY (event_id)
            REFERENCES events(id)
            ON DELETE CASCADE,
    attendee_id BIGSERIAL,
      CONSTRAINT fk_attendee_id
        FOREIGN KEY (attendee_id)
        REFERENCES user_profiles(id)
        ON DELETE CASCADE,
    attending_status varchar(63),
);