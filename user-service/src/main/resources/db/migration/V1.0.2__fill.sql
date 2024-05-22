--use userdb;

INSERT INTO users (username, password_hash, email, role, created_at, updated_at) VALUES
('john_doe', 'W3NHY0YPf2lbVW', 'john.doe@example.com', 'admin', '2024-05-01 10:00:00', '2024-05-10 15:30:00'),
('jane_smith', 'xTcP/2K5KI6PS', 'jane.smith@example.com', 'user', '2024-05-02 11:00:00', '2024-05-11 16:00:00'),
('alice_jones', 'Fz6qLJH7fVGe', 'alice.jones@example.com', 'moderator', '2024-05-03 12:00:00', '2024-05-12 17:00:00'),
('bob_brown', 'H6TqT0h4M.LC', 'bob.brown@example.com', 'user', '2024-05-04 13:00:00', '2024-05-13 18:00:00'),
('charlie_black', 'oL7c6P2J4H5Qq', 'charlie.black@example.com', 'admin', '2024-05-05 14:00:00', NULL);


INSERT INTO user_profiles (user_id, first_name, last_name, phone_number, address) VALUES
(1, 'john', 'doe', '6783456', 'Vilnius'),
(2, 'jane', 'smith', '6785678', 'Kaunas'),
(3, 'alice', 'jones', '6712345', 'Ryga'),
(4, 'bob', 'brown', '6456789', 'Vilnius'),
(5, 'charlie', 'black', '6709876', 'Vilnius');
