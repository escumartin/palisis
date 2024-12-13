INSERT INTO users (id, name, last_name, password, created, last_updated)
VALUES
    (1, 'John', 'Doe', 'password123', '2023-12-01 10:00:00', '2023-12-01 10:30:00'),
    (2, 'Jane', 'Smith', 'securepass', '2023-12-02 15:00:00', '2023-12-02 16:45:00'),
    (3, 'Alice', 'Johnson', 'alicepass', '2023-12-03 09:00:00', '2023-12-03 09:30:00'),
    (4, 'Bob', 'Brown', 'bobsecure', '2023-12-04 14:00:00', '2023-12-04 14:20:00'),
    (5, 'Charlie', 'Davis', 'charliepass', '2023-12-05 08:00:00', '2023-12-05 08:45:00');

INSERT INTO operation_lines (id, name, user_id)
VALUES
    (1, 'Line 1', 1),
    (2, 'Line 2', 1),
    (3, 'Line 3', 2),
    (4, 'Line 4', 2),
    (5, 'Line 5', 3),
    (6, 'Line 6', 4),
    (7, 'Line 7', 5);
