CREATE TABLE IF NOT EXISTS user_roles
(
    user_role_id UUID NOT NULL DEFAULT uuid_generate_v4(), -- user-role association unique identifier
    user_id UUID NOT NULL, -- user identifier
    role_id UUID NOT NULL, -- role identifier

    CONSTRAINT user_roles_pk PRIMARY KEY (user_role_id)
);

CREATE UNIQUE INDEX IF NOT EXISTS idx_user_roles_u1 ON user_roles (user_id, role_id);
