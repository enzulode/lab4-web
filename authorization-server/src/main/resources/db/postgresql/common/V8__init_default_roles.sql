-- create user role
INSERT INTO roles(role_code, role_description)
VALUES ('APP_USER', 'Ordinary user role');

-- defile APP_USER role granted authorities
INSERT INTO role_authorities(role_id, authority_id)
VALUES (
        (SELECT role_id FROM roles WHERE role_code = 'APP_USER'),
        (SELECT authority_id FROM authorities WHERE authority_code = 'AUTHORITY_GET_OWN_DATA')
       );

INSERT INTO role_authorities(role_id, authority_id)
VALUES (
        (SELECT role_id FROM roles WHERE role_code = 'APP_USER'),
        (SELECT authority_id FROM authorities WHERE authority_code = 'AUTHORITY_UPDATE_OWN_DATA')
       );

INSERT INTO role_authorities(role_id, authority_id)
VALUES (
        (SELECT role_id FROM roles WHERE role_code = 'APP_USER'),
        (SELECT authority_id FROM authorities WHERE authority_code = 'AUTHORITY_UPDATE_OWN_PASSWORD')
       );

INSERT INTO role_authorities(role_id, authority_id)
VALUES (
        (SELECT role_id FROM roles WHERE role_code = 'APP_USER'),
        (SELECT authority_id FROM authorities WHERE authority_code = 'AUTHORITY_DELETE_OWN_ACCOUNT')
       );
