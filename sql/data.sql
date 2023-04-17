-- Insert sample data into students table
INSERT INTO students (name, grade, birthday, notes) VALUES
    ('John Smith', 10, '2005-05-15', 'Likes math'),
    ('Emily Brown', 11, '2004-02-28', 'Plays the violin'),
    ('Michael Lee', 12, '2003-09-03', NULL),
    ('Sarah Jones', 9, '2006-11-12', 'Enjoys reading');

-- Insert sample data into courses table
INSERT INTO courses (name, lecturer, year, notes) VALUES
    ('Mathematics', 'Dr. James Johnson', 2022, 'Advanced level course'),
    ('English Literature', 'Prof. Jane Thompson', 2023, NULL),
    ('Chemistry', 'Dr. Peter Chen', 2022, 'Lab experiments required'),
    ('History', 'Prof. Susan Lee', 2023, 'Covers major events of the 20th century');

-- Insert sample data into score_board table
INSERT INTO score_board (student_id, course_id, score) VALUES
    (1, 1, 90.5),
    (1, 3, 87.2),
    (2, 1, 78.9),
    (2, 2, 94.3),
    (2, 4, 85.6),
    (3, 2, 91.2),
    (3, 3, 80.1),
    (4, 1, 84.7),
    (4, 4, 92.0);