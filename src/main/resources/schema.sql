CREATE TABLE IF NOT EXISTS USERS (
  user_id SERIAL PRIMARY KEY,
  username VARCHAR(20),
  salt VARCHAR,
  password VARCHAR,
  first_name VARCHAR(20),
  last_name VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS NOTES (
    note_id SERIAL PRIMARY KEY,
    note_title VARCHAR(20),
    note_description VARCHAR (1000),
    user_id INT,
    foreign key (user_id) references USERS(user_id)
);

CREATE TABLE IF NOT EXISTS FILES (
    file_id SERIAL PRIMARY KEY,
    file_name VARCHAR,
    content_type VARCHAR,
    file_size VARCHAR,
    user_id INT,
    file_data BYTEA,
    foreign key (user_id) references USERS(user_id)
);

CREATE TABLE IF NOT EXISTS CREDENTIALS (
    credential_id SERIAL PRIMARY KEY,
    url VARCHAR(100),
    username VARCHAR (30),
    key VARCHAR,
    password VARCHAR,
    user_id INT,
    foreign key (user_id) references USERS(user_id)
);