ALTER TABLE user_profiles
    DROP CONSTRAINT fk_user;

ALTER TABLE user_profiles
    ALTER COLUMN user_id TYPE BIGINT;

ALTER TABLE user_profiles
    ADD CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id);


ALTER TABLE contacts
    DROP CONSTRAINT fk_user1;

ALTER TABLE contacts
    ALTER COLUMN user_id1 TYPE BIGINT;

ALTER TABLE contacts
    ADD CONSTRAINT fk_user1 FOREIGN KEY (user_id1) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE contacts
    DROP CONSTRAINT fk_user2;

ALTER TABLE contacts
    ALTER COLUMN user_id2 TYPE BIGINT;

ALTER TABLE contacts
    ADD CONSTRAINT fk_user2 FOREIGN KEY (user_id2) REFERENCES user_profiles(id) ON DELETE CASCADE;


ALTER TABLE events
    DROP CONSTRAINT fk_user;

ALTER TABLE events
    ALTER COLUMN user_id TYPE BIGINT;

ALTER TABLE events
    ADD CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;