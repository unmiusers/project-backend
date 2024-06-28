CREATE DATABASE project;

CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(255) NOT NULL UNIQUE,
                                     password VARCHAR(255) NOT NULL,
                                     email VARCHAR(255) NOT NULL UNIQUE,
                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS issues (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      title VARCHAR(255) NOT NULL,
                                      description TEXT,
                                      status VARCHAR(50),
                                      priority VARCHAR(50),
                                      type VARCHAR(50),
                                      custom_fields JSON,
                                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS comments (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        issue_id BIGINT,
                                        content TEXT,
                                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                        FOREIGN KEY (issue_id) REFERENCES issues(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS gantt_tasks (
                                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           name VARCHAR(255) NOT NULL,
                                           start DATE NOT NULL,
                                           end DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS wiki_pages (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          title VARCHAR(255) NOT NULL,
                                          content TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS commits (
                                       id VARCHAR(255) PRIMARY KEY,
                                       message TEXT NOT NULL,
                                       author VARCHAR(255),
                                       date TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS reports (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       issue VARCHAR(255),
                                       status VARCHAR(50),
                                       task VARCHAR(255),
                                       completion INT
);

CREATE TABLE IF NOT EXISTS queries (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       name VARCHAR(255) NOT NULL,
                                       filters TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS timelogs (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        description TEXT,
                                        hours INT,
                                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS notifications (
                                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                             message TEXT,
                                             date TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS login_history (
                                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                             user_id BIGINT,
                                             date TIMESTAMP NOT NULL,
                                             ip VARCHAR(255),
                                             FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);