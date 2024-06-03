use devicedb;

DROP TABLE IF EXISTS devices;
DROP TABLE IF EXISTS device_logs;


CREATE TABLE devices (
    device_id bigint(20) NOT NULL AUTO_INCREMENT,
    user_id bigint(20),
    device_name VARCHAR(255),
    device_type VARCHAR(255),
    model VARCHAR(255),
    location VARCHAR(255),
    status VARCHAR(50),
    created_at datetime NOT NULL,
    updated_at datetime DEFAULT NULL,
    PRIMARY KEY (device_id)
);

CREATE TABLE device_logs (
    log_id bigint(20) NOT NULL AUTO_INCREMENT,
    device_id bigint(20),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    log_message TEXT,
    FOREIGN KEY (device_id) REFERENCES devices(device_id),
    PRIMARY KEY (log_id)
);


