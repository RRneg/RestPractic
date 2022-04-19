CREATE TABLE users (
id INT NOT_NULL AUTO_INCREMENT,
user_name VARCHAR (255)
PRIMARY KEY(id) );

CREATE TABLE files (
id INT NOT_NULL  AUTO_INCREMENT,
file_path VARCHAR(1000),
file_name VARCHAR(255),
file_size INT,
PRIMARY KEY(id)
);

CREATE TABLE events (
id INT NOT_NULL PRIMARY KEY AUTO_INCREMENT,
event_status VARCHAR(30),
updated DATETIME NOW(),
created DATETIME NOW(),
user_id INT,
file_id INT,
FOREIGN KEY(user_id) REFERENCES users(id),
FOREIGN KEY(file_id) REFERENCES files(id)
);
