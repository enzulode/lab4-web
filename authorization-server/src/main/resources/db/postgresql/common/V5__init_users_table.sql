CREATE TABLE IF NOT EXISTS users
(
    user_id UUID NOT NULL DEFAULT uuid_generate_v4(), -- user unique identifier
    email VARCHAR(127) NOT NULL, -- user email
    password_hash VARCHAR(511), -- user hashed password
    first_name VARCHAR(127) NOT NULL, -- user firstname
    last_name VARCHAR(127) NOT NULL, -- user lastname
    middle_name VARCHAR(127), -- user middle name
    profile_img VARCHAR(255), -- user profile img
    active BOOLEAN NOT NULL DEFAULT true, -- user activeness status (true by default)
--     TODO: user registration should be verified. 'active' property should be false by default

    CONSTRAINT users_pk PRIMARY KEY (user_id)
);

CREATE UNIQUE INDEX IF NOT EXISTS idx_user_u1 ON users (email);
