DROP TABLE IF EXISTS rolling_codes;

CREATE TABLE rolling_codes (
    code  int PRIMARY KEY,
    expiry_time TIMESTAMP WITH TIME ZONE,
    user_id bigserial NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);