CREATE TABLE users
(
    id        INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_name CHARACTER VARYING(255)
);

CREATE TABLE files
(
    id        INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    file_path CHARACTER VARYING(1000),
    file_name CHARACTER VARYING(255),
    file_size INTEGER
);

CREATE TABLE events
(
    id           INTEGER   NOT NULL AUTO_INCREMENT PRIMARY KEY,
    event_status CHARACTER VARYING(30),
    created      TIMESTAMP NOT NULL DEFAULT NOW(),
    updated      TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id      INTEGER,
    file_id      INTEGER,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_file_id FOREIGN KEY (file_id) REFERENCES files (id)
);
