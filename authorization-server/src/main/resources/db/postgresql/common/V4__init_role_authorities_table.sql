CREATE TABLE IF NOT EXISTS role_authorities
(
    role_authority_id UUID NOT NULL DEFAULT uuid_generate_v4(), -- role-authority association unique identifier
    role_id UUID NOT NULL, -- role identifier
    authority_id UUID NOT NULL, -- authority identifier

    CONSTRAINT role_authorities_pk PRIMARY KEY (role_authority_id)
);

CREATE UNIQUE INDEX IF NOT EXISTS idx_role_authorities_u1 ON role_authorities (role_id, authority_id);
