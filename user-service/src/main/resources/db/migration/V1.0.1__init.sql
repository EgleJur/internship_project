use userdb;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS user_profiles;

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

CREATE TABLE user_profiles (
    profile_id bigint(20) NOT NULL AUTO_INCREMENT,
    user_id bigint(20) NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    phone_number VARCHAR(20),
    address VARCHAR(255),
    PRIMARY KEY (profile_id),
    UNIQUE KEY (user_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

