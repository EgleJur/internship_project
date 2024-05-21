use userdb;

--DROP TABLE IF EXISTS users;

CREATE TABLE users (
    user_id bigint(20) NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255),
    email VARCHAR(255),
    role VARCHAR(50),
    created_at datetime NOT NULL,
    updated_at datetime DEFAULT NULL,
     PRIMARY KEY (user_id)
);
