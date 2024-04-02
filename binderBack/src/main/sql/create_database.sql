CREATE TABLE IF NOT EXISTS Users (
    id SERIAL PRIMARY KEY,
    contacts VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    login VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    photo BYTEA
);

CREATE TABLE IF NOT EXISTS Book (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    edition VARCHAR(255),
    description TEXT,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES User(id)
);

CREATE TABLE IF NOT EXISTS Match (
    id SERIAL PRIMARY KEY,
    book_id_1 INT,
    book_id_2 INT,
    time TIMESTAMP,
    FOREIGN KEY (book_id_1) REFERENCES Book(id),
    FOREIGN KEY (book_id_2) REFERENCES Book(id)
);

CREATE TABLE IF NOT EXISTS Genre (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS BookGenre (
    id SERIAL PRIMARY KEY,
    book INT,
    genre INT,
    FOREIGN KEY (book) REFERENCES Book(id),
    FOREIGN KEY (genre) REFERENCES Genre(id)
);