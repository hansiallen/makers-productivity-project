

ALTER TABLE user_links
DROP CONSTRAINT user_links_user_id_fkey;

ALTER TABLE event_attendees
DROP CONSTRAINT fk_attendee;

ALTER TABLE contacts
DROP CONSTRAINT fk_user2;

ALTER TABLE custom_fields
DROP CONSTRAINT custom_fields_user_profile_id_fkey;

ALTER TABLE user_profiles
DROP CONSTRAINT user_profiles_pkey;
ALTER TABLE user_profiles
DROP CONSTRAINT fk_user;
ALTER TABLE user_profiles
DROP COLUMN id;

DROP TABLE custom_fields;

CREATE TABLE custom_fields (
    user_id bigserial PRIMARY KEY,
    custom_info_key VARCHAR(255),
    custom_info_content VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);


ALTER TABLE user_profiles
ADD PRIMARY KEY (user_id);

ALTER TABLE user_links
RENAME COLUMN user_profile_id TO user_id;

ALTER TABLE custom_fields
    ADD CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE user_profiles
    ADD CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE user_links
    ADD CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE event_attendees
    ADD CONSTRAINT fk_attendee FOREIGN KEY (attendee_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE contacts
    ADD CONSTRAINT fk_user2 FOREIGN KEY (user_id2) REFERENCES users(id) ON DELETE CASCADE;