CREATE TABLE limits (
    id SERIAL PRIMARY KEY,
    user_id INT UNIQUE NOT NULL,
    amount NUMERIC
);

INSERT INTO limits (user_id, amount)
VALUES
    (1, 10000),
    (2, 10000),
    (3, 10000),
    (4, 10000),
    (5, 10000),
    (6, 10000),
    (7, 10000),
    (8, 10000),
    (9, 10000),
    (10, 10000);
