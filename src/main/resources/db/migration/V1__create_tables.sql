CREATE TABLE IF NOT EXISTS person
(
    id         BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name       VARCHAR(255) NOT NULL,
    created_at DATE,
    is_removed BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS task
(
    id          BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    start_date  DATE,
    end_date    DATE,
    description VARCHAR(500),
    is_removed  BOOLEAN DEFAULT FALSE,
    user_id     BIGINT,
    FOREIGN KEY (user_id) REFERENCES person (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS file
(
    id         BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    file_name  VARCHAR(255) NOT NULL,
    is_removed BOOLEAN DEFAULT FALSE,
    task_id    BIGINT,
    FOREIGN KEY (task_id) REFERENCES task (id) ON DELETE CASCADE
);