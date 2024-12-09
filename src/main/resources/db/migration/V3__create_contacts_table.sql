DROP TABLE IF EXISTS contacts;

CREATE TABLE contacts (
    user_id1 BIGSERIAL PRIMARY KEY,
          CONSTRAINT fk_user
            FOREIGN KEY (user_id1)
            REFERENCES user_profiles(id)
            ON DELETE CASCADE,
    user_id2 BIGSERIAL,
      CONSTRAINT fk_user
        FOREIGN KEY (user_id2)
        REFERENCES user_profiles(id)
        ON DELETE CASCADE
);
