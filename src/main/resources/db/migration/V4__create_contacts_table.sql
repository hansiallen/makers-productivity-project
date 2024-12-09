DROP TABLE IF EXISTS contacts;

CREATE TABLE contacts (
    user_id1 BIGSERIAL PRIMARY KEY,
          CONSTRAINT fk_user1
            FOREIGN KEY (user_id1)
            REFERENCES users(id)
            ON DELETE CASCADE,
    user_id2 BIGSERIAL,
      CONSTRAINT fk_user2
        FOREIGN KEY (user_id2)
        REFERENCES user_profiles(id)
        ON DELETE CASCADE
);
