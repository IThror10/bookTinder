CREATE TABLE IF NOT EXISTS Reader (
    id SERIAL PRIMARY KEY,
    contacts TEXT NOT NULL,
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
    description TEXT
);

CREATE TABLE IF NOT EXISTS GiveAway (
    id SERIAL PRIMARY KEY,
    book_id INT,
    user_id INT,
    description TEXT,
    photo BYTEA,
    FOREIGN KEY (book_id) REFERENCES Book(id),
    FOREIGN KEY (user_id) REFERENCES Reader(id)
);

CREATE TABLE IF NOT EXISTS MatchResult (
    id SERIAL PRIMARY KEY,
    giveaway_id INT,
    user_id INT,
    liked BOOLEAN NOT NULL,
    time TIMESTAMP NOT NULL,
    FOREIGN KEY (giveaway_id) REFERENCES GiveAway(id),
    FOREIGN KEY (user_id) REFERENCES Reader(id)
);

CREATE TABLE IF NOT EXISTS UserStory (
    id SERIAL PRIMARY KEY,
    book_id INT,
    user_id INT,
    liked BOOLEAN NOT NULL,
    FOREIGN KEY (book_id) REFERENCES Book(id),
    FOREIGN KEY (user_id) REFERENCES Reader(id)
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

