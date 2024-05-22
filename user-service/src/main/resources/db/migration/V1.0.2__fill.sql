--use userdb;

INSERT INTO users (username, password_hash, email, role, created_at, updated_at) VALUES
('john_doe', 'W3NHY0YPf2lbVW', 'john.doe@example.com', 'admin', '2024-05-01 10:00:00', '2024-05-10 15:30:00'),
('jane_smith', 'xTcP/2K5KI6PS', 'jane.smith@example.com', 'user', '2024-05-02 11:00:00', '2024-05-11 16:00:00'),
('alice_jones', 'Fz6qLJH7fVGe', 'alice.jones@example.com', 'moderator', '2024-05-03 12:00:00', '2024-05-12 17:00:00'),
('bob_brown', 'H6TqT0h4M.LC', 'bob.brown@example.com', 'user', '2024-05-04 13:00:00', '2024-05-13 18:00:00'),
('charlie_black', 'oL7c6P2J4H5Qq', 'charlie.black@example.com', 'admin', '2024-05-05 14:00:00', NULL);
