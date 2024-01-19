-- create user and assign role
INSERT INTO users (email, password_hash, first_name, last_name, middle_name, profile_img, active)
VALUES ('enzulode@gmail.com', '{bcrypt}$2a$12$R6ChQ0FZ0tXj6Dd3CNDh8e7FsKIuqcgp2rH5bq2q8vZCvUMCCyQw2', 'Petrov', 'Ivan', 'Sergeevich', null, true);

INSERT INTO user_roles(user_id, role_id)
VALUES (
        (SELECT u.user_id FROM users AS u WHERE u.email = 'enzulode@gmail.com'),
        (SELECT r.role_id FROM roles AS r WHERE r.role_code = 'APP_USER')
       );

-- create user and assign role
INSERT INTO users (email, password_hash, first_name, last_name, middle_name, profile_img, active)
VALUES ('admin@gmail.com', '{bcrypt}$2a$12$R6ChQ0FZ0tXj6Dd3CNDh8e7FsKIuqcgp2rH5bq2q8vZCvUMCCyQw2', 'Alexeev', 'Semen', 'Ivanovich', null, true);

INSERT INTO user_roles(user_id, role_id)
VALUES (
        (SELECT u.user_id FROM users AS u WHERE u.email = 'admin@gmail.com'),
        (SELECT r.role_id FROM roles AS r WHERE r.role_code = 'APP_USER')
       );
