DROP TABLE IF EXISTS custom_fields;

CREATE TABLE custom_fields (
    id bigserial PRIMARY KEY,
    user_profile_id bigint NOT NULL,
    custom_info_key VARCHAR(255),
    custom_info_content VARCHAR(255),
    FOREIGN KEY (user_profile_id) REFERENCES user_profiles(id) ON DELETE CASCADE
);
