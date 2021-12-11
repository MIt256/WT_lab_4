DROP TABLE IF EXISTS reservation CASCADE;
DROP TABLE IF EXISTS "user";
DROP TABLE IF EXISTS authentication;
DROP TABLE IF EXISTS role;
DROP TYPE IF EXISTS USER_ROLE;
DROP TABLE IF EXISTS room;

CREATE TYPE USER_ROLE as enum ('ADMIN', 'USER');

CREATE TABLE role
(
    id   SERIAL PRIMARY KEY,
    role USER_ROLE UNIQUE NOT NULL
);

INSERT INTO role(role)
VALUES ('ADMIN'),
       ('USER');

CREATE TABLE "user"
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(64) UNIQUE NOT NULL,
    password VARCHAR(128)       NOT NULL,
    role_id  INTEGER            NOT NULL REFERENCES role (id)
);

CREATE TABLE authentication
(
    id    SERIAL PRIMARY KEY,
    value VARCHAR(256)
);

CREATE TABLE room
(
    id          SERIAL PRIMARY KEY,
    label       INTEGER NOT NULL,
    has_kitchen BOOLEAN,
    has_bath    BOOLEAN

);

CREATE TABLE reservation
(
    id      SERIAL PRIMARY KEY,
    room_id INTEGER UNIQUE NOT NULL REFERENCES room (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
    user_id INTEGER NOT NULL REFERENCES "user" (id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

ALTER TABLE room
    ADD COLUMN reservation_id INTEGER REFERENCES reservation (id);

INSERT INTO room(label, has_kitchen, has_bath)
VALUES (1, false, false),
       (2, false, true),
       (3, true, false),
       (4, true, true);