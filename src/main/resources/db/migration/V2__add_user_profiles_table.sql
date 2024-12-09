DROP TABLE IF EXISTS user_profiles;

CREATE TABLE user_profiles (
    id bigserial PRIMARY KEY,
    first_name VARCHAR(100),
    middle_name VARCHAR(100),
    last_name VARCHAR(100),
    preferred_name VARCHAR(100),
    profile_photo_url TEXT,
    user_id bigserial,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
);