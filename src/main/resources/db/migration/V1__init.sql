CREATE TABLE limits (
    id SERIAL PRIMARY KEY,
    user_id INT UNIQUE NOT NULL,
    amount NUMERIC
);

CREATE TABLE write_downs (
    id SERIAL PRIMARY KEY,
    limit_id INT NOT NULL,
    amount NUMERIC NOT NULL,
    create_date TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL
);

ALTER TABLE write_downs
    ADD CONSTRAINT write_downs FOREIGN KEY (limit_id) REFERENCES limits(id);

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
