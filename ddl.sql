DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS users_roles CASCADE;
DROP TABLE IF EXISTS notebooks CASCADE;
DROP TABLE IF EXISTS notes CASCADE;
DROP TABLE IF EXISTS tags CASCADE;
DROP TABLE IF EXISTS notes_tags CASCADE;
DROP TABLE IF EXISTS users_tags CASCADE;
DROP TABLE IF EXISTS attachments CASCADE;

CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username varchar(40) UNIQUE NOT NULL,
                       password varchar(60) NOT NULL,
                       refresh_token varchar(256),
                       creation_timestamp timestamp
);

CREATE TABLE roles (
                       id SERIAL PRIMARY KEY,
                       name varchar(40) UNIQUE NOT NULL
);

CREATE TABLE users_roles (
                             user_id int,
                             role_id int
);

CREATE TABLE notebooks (
                           id SERIAL PRIMARY KEY,
                           user_id int,
                           title varchar(400),
                           enc_key varchar(128),
                           creation_timestamp timestamp
);

CREATE TABLE notes (
                       id SERIAL PRIMARY KEY,
                       notebook_id int,
                       title varchar(400),
                       content bytea,
                       creation_timestamp timestamp
);

CREATE TABLE tags (
                      id SERIAL PRIMARY KEY,
                      name varchar(100) UNIQUE NOT NULL
);

CREATE TABLE notes_tags (
                            note_id int,
                            tag_id int
);

CREATE TABLE users_tags (
                            user_id int,
                            tag_id int
);

CREATE TABLE attachments (
                             id SERIAL PRIMARY KEY,
                             title varchar(256),
                             note_id int,
                             content bytea
);

ALTER TABLE notebooks ADD FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE notes ADD FOREIGN KEY (notebook_id) REFERENCES notebooks (id);

ALTER TABLE notes_tags ADD FOREIGN KEY (note_id) REFERENCES notes (id);

ALTER TABLE notes_tags ADD FOREIGN KEY (tag_id) REFERENCES tags (id);

ALTER TABLE users_tags ADD FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE users_tags ADD FOREIGN KEY (tag_id) REFERENCES tags (id);

ALTER TABLE users_roles ADD FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE users_roles ADD FOREIGN KEY (role_id) REFERENCES roles (id);

ALTER TABLE attachments ADD FOREIGN KEY (note_id) REFERENCES notes (id);

CREATE INDEX user_name ON users (name);
