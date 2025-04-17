CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE chat_rooms (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(255) NOT NULL
);

CREATE TABLE messages (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          chat_room_id BIGINT NOT NULL,
                          content TEXT NOT NULL,
                          sender_id BIGINT NOT NULL,
                          FOREIGN KEY (chat_room_id) REFERENCES chat_rooms(id),
                          FOREIGN KEY (sender_id) REFERENCES users(id)
);

INSERT INTO users (username, password)
VALUES ('admin', 'admin123');