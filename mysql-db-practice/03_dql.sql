-- ============================================================================
-- MySQL Practice Tasks: DQL (Data Query Language)
-- Database: school_db
-- ============================================================================

USE school_db;

-- Task 1: Filtering and sorting [7 marks]
-- 1. Display all students.
SELECT *
FROM students;

-- 2. Display only name and hometown, with the column name shown as Student Name.
SELECT
    name AS 'Student Name',
    hometown
FROM students;

-- 3. List students aged between 10 and 15.
SELECT *
FROM students
WHERE age BETWEEN 10 AND 15;

-- 4. List students whose name starts with 'A'.
SELECT *
FROM students
WHERE name LIKE 'A%';

-- 5. List students from Delhi or Noida, sorted by age in descending order.
SELECT *
FROM students
WHERE hometown IN ('Delhi', 'Noida')
ORDER BY age DESC;

-- 6. Show the 3 oldest students.
SELECT *
FROM students
ORDER BY age DESC
LIMIT 3;

-- 7. Show the distinct cities (renamed to hometown in DDL Task 2.4).
SELECT DISTINCT hometown
FROM students;


-- Task 2: Aggregates and joins [8 marks]
-- 1. Count how many students are in each city.
SELECT
    hometown,
    COUNT(*) AS student_count
FROM students
GROUP BY hometown;

-- 2. Find the average, highest and lowest score for each subject.
SELECT
    subject,
    ROUND(AVG(score), 2) AS average_score,
    MAX(score) AS highest_score,
    MIN(score) AS lowest_score
FROM marks
GROUP BY subject;

-- 3. Show only the subjects whose average score is above 60 (use HAVING).
SELECT
    subject,
    ROUND(AVG(score), 2) AS average_score
FROM marks
GROUP BY subject
HAVING AVG(score) > 60;

-- 4. Display student name, subject and score using an INNER JOIN.
SELECT
    s.name AS student_name,
    m.subject,
    m.score
FROM students s
INNER JOIN marks m ON s.student_id = m.student_id;

-- 5. Display all students, including those with no marks, using a LEFT JOIN.
SELECT
    s.student_id,
    s.name AS student_name,
    m.subject,
    m.score
FROM students s
LEFT JOIN marks m ON s.student_id = m.student_id;

-- 6. Find students who scored above the overall average (use a subquery).
SELECT DISTINCT
    s.student_id,
    s.name
FROM students s
INNER JOIN marks m ON s.student_id = m.student_id
WHERE m.score > (
    SELECT AVG(score)
    FROM marks
);

-- 7. Challenge: Rank the students by total marks using RANK().
SELECT
    s.student_id,
    s.name,
    COALESCE(SUM(m.score), 0) AS total_marks,
    RANK() OVER (ORDER BY COALESCE(SUM(m.score), 0) DESC) AS student_rank
FROM students s
LEFT JOIN marks m ON s.student_id = m.student_id
GROUP BY s.student_id, s.name;
