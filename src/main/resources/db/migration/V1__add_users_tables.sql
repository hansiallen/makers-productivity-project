DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id bigserial PRIMARY KEY,
    auth0_id VARCHAR(255) UNIQUE,
    email VARCHAR(100) NOT NULL
);