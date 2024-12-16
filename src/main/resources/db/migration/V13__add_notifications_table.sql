DROP TABLE IF EXISTS notifications;

CREATE TABLE notifications (
  id BIGSERIAL PRIMARY KEY,
  receiver_id BIGINT,
  sender_id BIGINT,
  type VARCHAR(20),
  content VARCHAR(255),
  is_read BOOLEAN DEFAULT FALSE,
  event_id BIGINT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_receiver FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_sender FOREIGN KEY (sender_id) REFERENCES user_profiles(user_id) ON DELETE CASCADE,
  CONSTRAINT fk_event FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE
);

-- event_id set to bigint or null. if notification relates to an event, event_id will be added, otherwise will be null