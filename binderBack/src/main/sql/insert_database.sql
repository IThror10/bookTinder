INSERT INTO Reader (contacts, name, login, password, photo)
VALUES
('test1@example.com', 'Alice Smith', 'alicesmith', '$2a$10$6cIqQDNTCE61Ssmm0Vq/t.Dw/T3oBNiNlpDJ0xkXZGPykB0ToJEWi', NULL),
('test2@example.com', 'Bob Johnson', 'bjohnson', '$2a$10$6cIqQDNTCE61Ssmm0Vq/t.Dw/T3oBNiNlpDJ0xkXZGPykB0ToJEWi', NULL),
('test3@example.com', 'Emily Brown', 'ebrown', '$2a$10$6cIqQDNTCE61Ssmm0Vq/t.Dw/T3oBNiNlpDJ0xkXZGPykB0ToJEWi', NULL),
('test4@example.com', 'David Wilson', 'dwilson', '$2a$10$6cIqQDNTCE61Ssmm0Vq/t.Dw/T3oBNiNlpDJ0xkXZGPykB0ToJEWi', NULL),
('test5@example.com', 'Olivia Davis', 'odavis', '$2a$10$6cIqQDNTCE61Ssmm0Vq/t.Dw/T3oBNiNlpDJ0xkXZGPykB0ToJEWi', NULL);


INSERT INTO Book (title, author, edition, description)
VALUES
('To Kill a Mockingbird', 'Harper Lee', '50th Anniversary Edition', 'A classic novel set in the American South.'),
('1984', 'George Orwell', 'First Edition', 'A dystopian novel about totalitarianism.'),
('Pride and Prejudice', 'Jane Austen', 'Revised Edition', 'A romantic novel set in the Regency era.'),
('The Catcher in the Rye', 'J.D. Salinger', 'Modern Library Edition', 'A coming-of-age novel.'),
('Moby-Dick', 'Herman Melville', 'Norton Critical Edition', 'A tale of obsession and revenge.');


INSERT INTO GiveAway (book_id, user_id, description, photo)
VALUES
(1, 1, 'Free book giveaway!', E'\\x010203'),
(2, 2, 'Limited time offer!', E'\\x040506'),
(3, 3, 'Join our book club!', E'\\x070809'),
(4, 4, 'Get your copy now!', E'\\x0a0b0c'),
(5, 5, 'Special edition giveaway!', E'\\x0d0e0f');

INSERT INTO UserStory (user_id, book_id, liked)
VALUES
(1, 1, TRUE),
(1, 2, TRUE),
(2, 3, FALSE),
(2, 4, FALSE),
(5, 3, TRUE),
(5, 4, FALSE)
(5, 5, TRUE);

INSERT INTO UserStory (user_id, book_id, liked)
VALUES
(1, 3, TRUE, '2022-10-15 18:30:00'),
(2, 1, TRUE, '2022-10-16 14:00:00'),
(3, 3, FALSE, '2022-10-17 20:45:00');
