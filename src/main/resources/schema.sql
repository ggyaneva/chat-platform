CREATE TABLE IF NOT EXISTS chat_room (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS chat_message (
                                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                            sender VARCHAR(255) NOT NULL,
                                            content TEXT NOT NULL,
                                            timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                            chat_room_id BIGINT NOT NULL,
                                            FOREIGN KEY (chat_room_id) REFERENCES chat_room(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(255) NOT NULL UNIQUE,
                                     password VARCHAR(255) NOT NULL,
                                     role VARCHAR(50) NOT NULL
);