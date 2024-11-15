CREATE TABLE IF NOT EXISTS user_entity
(
    id         BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name       VARCHAR(255) NOT NULL,
    created_at DATE,
    is_removed BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS task_entity
(
    id          BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    start_date  DATE,
    end_date    DATE,
    description VARCHAR(500),
    is_removed  BOOLEAN DEFAULT FALSE,
    user_id     BIGINT,
    tags VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES user_entity (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS tag_entity
(
    id   BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS task_tag
(
    task_id BIGINT,
    tag_id  BIGINT,
    PRIMARY KEY (task_id, tag_id),
    FOREIGN KEY (task_id) REFERENCES task_entity (id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag_entity (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS file_entity
(
    id         BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    file_name  VARCHAR(255) NOT NULL,
    is_removed BOOLEAN DEFAULT FALSE,
    task_id    BIGINT,
    FOREIGN KEY (task_id) REFERENCES task_entity (id) ON DELETE CASCADE
);