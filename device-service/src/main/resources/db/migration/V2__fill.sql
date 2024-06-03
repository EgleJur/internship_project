use devicedb;

INSERT INTO devices (user_id, device_name, device_type, model, location, status, created_at)
VALUES
(1, 'Thermostat', 'Climate Control', 'ThermoX-2000', 'Living Room', 'Active', '2024-05-01 10:00:00'),
(2, 'Security Camera', 'Surveillance', 'SecureCam-100', 'Front Door', 'Active', '2024-05-02 11:00:00'),
(1, 'Smart Light', 'Lighting', 'LightX-300', 'Bedroom', 'Inactive', '2024-05-04 13:00:00');

INSERT INTO device_logs (device_id, log_message)
VALUES
(1, 'Device initialized.'),
(1, 'Temperature set to 72°F.'),
(2, 'Motion detected.'),
(2, 'Recording started.'),
(3, 'Light turned on.'),
(3, 'Light turned off.');

