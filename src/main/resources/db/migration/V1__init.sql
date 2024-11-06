CREATE TABLE limits (
    id SERIAL PRIMARY KEY,
    user_id INT UNIQUE NOT NULL,
    amount NUMERIC
);

CREATE TABLE operations (
    id SERIAL PRIMARY KEY,
    limit_id INT NOT NULL,
    amount NUMERIC NOT NULL,
    create_date TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL
);

ALTER TABLE operations
    ADD CONSTRAINT operations FOREIGN KEY (limit_id) REFERENCES limits(id);
