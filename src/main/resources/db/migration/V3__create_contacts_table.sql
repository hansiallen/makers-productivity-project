DROP TABLE IF EXISTS contacts;

CREATE TABLE contacts (
    user_id1 BIGSERIAL PRIMARY KEY,
    user_id2 BIGSERIAL,
      CONSTRAINT fk_user
        FOREIGN KEY (user_id2)
        REFERENCES users(id)
        ON DELETE CASCADE
);
