CREATE TABLE emails
(
    id       UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    receiver VARCHAR(255) UNIQUE NOT NULL
);