DROP TABLE IF EXISTS events;

CREATE TABLE events (
    id bigserial PRIMARY KEY,
    date DATE NOT NULL,
    start_time TIME,
    end_time TIME,
    title VARCHAR(255),
    description TEXT,
    user_id bigserial NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
