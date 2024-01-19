CREATE TABLE IF NOT EXISTS roles
(
    role_id UUID NOT NULL DEFAULT uuid_generate_v4(), -- unique role identifier
    role_code VARCHAR(63) NOT NULL, -- role code stands for role name (e.g. USER)
    role_description VARCHAR(511) NOT NULL, -- describes a set of allowed operations with this role
    active BOOLEAN NOT NULL DEFAULT true, -- is the role active at the moment

    CONSTRAINT roles_pk PRIMARY KEY (role_code)
);

CREATE UNIQUE INDEX IF NOT EXISTS idx_roles_u1 ON roles (role_code);
