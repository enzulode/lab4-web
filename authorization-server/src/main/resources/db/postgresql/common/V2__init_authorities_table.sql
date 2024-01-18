CREATE TABLE IF NOT EXISTS authorities
(
    authority_id UUID NOT NULL default uuid_generate_v4(), -- unique authority identifier
    authority_code VARCHAR(127) NOT NULL, -- authority code stands for authority name (e.g. AUTHORITY_GET_OWN_DATA)
    authority_description VARCHAR(511) NOT NULL, -- describes allowed operations with this authority
    active BOOLEAN NOT NULL DEFAULT true, -- if the authority active at the moment

    CONSTRAINT authorities_pk PRIMARY KEY (authority_id)
);

CREATE UNIQUE INDEX IF NOT EXISTS idx_authorities_u1 ON authorities (authority_code);
