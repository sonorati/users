CREATE TABLE IF NOT EXISTS USER(
    id                 UUID NOT NULL,
    title              VARCHAR(255),
    first_name         VARCHAR(255) NOT NULL,
    surname          VARCHAR(255) NOT NULL,
    job_title          VARCHAR(255) NOT NULL,
    dob                VARCHAR(255),
    created_date  TIMESTAMP NOT NULL,
    updated_date  TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);
