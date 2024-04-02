INSERT INTO User (contacts, name, login, password, photo)
VALUES
('user1@mail.com', 'User One', 'user1', 'password1', NULL),
('user2@mail.com', 'User Two', 'user2', 'password2', NULL),
('user3@mail.com', 'User Three', 'user3', 'password3', NULL),
('user4@mail.com', 'User Four', 'user4', 'password4', NULL),
('user5@mail.com', 'User Five', 'user5', 'password5', NULL);

INSERT INTO Book (title, author, edition, description, user_id)
VALUES
('Book One', 'Author One', 'First Edition', 'Description of Book One', 1),
('Book Two', 'Author Two', 'Second Edition', 'Description of Book Two', 2),
('Book Three', 'Author Three', 'Third Edition', 'Description of Book Three', 3),
('Book Four', 'Author Four', 'Fourth Edition', 'Description of Book Four', 4),
('Book Five', 'Author Five', 'Fifth Edition', 'Description of Book Five', 5);

INSERT INTO Genre (name)
VALUES
('Fiction'),
('Non-Fiction'),
('Mystery'),
('Science Fiction'),
('Romance');

INSERT INTO BookGenre (book, genre)
VALUES
(1, 1),
(1, 3),
(2, 2),
(3, 3),
(4, 4),
(5, 3),
(5, 5);

INSERT INTO Match (book_id_1, book_id_2, time)
VALUES
(1, 2, '2022-10-15 18:30:00'),
(3, 4, '2022-10-16 14:00:00'),
(5, 6, '2022-10-17 20:45:00');