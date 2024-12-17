INSERT INTO users (name, last_name, password, created, last_updated)
VALUES
    ('John', 'Doe', 'password123', '2023-12-01 10:00:00', '2023-12-01 10:30:00'),
    ('Jane', 'Smith', 'securepass', '2023-12-02 15:00:00', '2023-12-02 16:45:00'),
    ('Alice', 'Johnson', 'alicepass', '2023-12-03 09:00:00', '2023-12-03 09:30:00'),
    ('Bob', 'Brown', 'bobsecure', '2023-12-04 14:00:00', '2023-12-04 14:20:00'),
    ('Charlie', 'Davis', 'charliepass', '2023-12-05 08:00:00', '2023-12-05 08:45:00');

INSERT INTO operation_lines (name, user_id)
VALUES
    ('Line 1', 1),
    ('Line 2', 1),
    ('Line 3', 2),
    ('Line 4', 2),
    ('Line 5', 3),
    ('Line 6', 4),
    ('Line 7', 5);
