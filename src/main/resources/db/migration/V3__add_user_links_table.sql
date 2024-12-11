DROP TABLE IF EXISTS user_links;

CREATE TABLE user_links (
    id bigserial PRIMARY KEY,
    user_id bigint NOT NULL,
    website_link_name VARCHAR(255),
    website_link_url VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES user_profiles(id) ON DELETE CASCADE
);
