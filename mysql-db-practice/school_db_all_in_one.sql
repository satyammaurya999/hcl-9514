-- ============================================================================
-- MySQL Practice Tasks: DDL (Data Definition Language)
-- Database: school_db
-- ============================================================================

-- Task 1: Create the structure [5 marks]
-- 1. Create a database school_db and select it.
CREATE DATABASE IF NOT EXISTS school_db;
USE school_db;

-- 2. Create a table students with required columns and constraints.
CREATE TABLE students (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE,
    age INT CHECK (age >= 5),
    city VARCHAR(50) DEFAULT 'Delhi',
    admission_date DATE
);

-- Task 2: Modify the structure [5 marks]
-- 1. Create a table marks with mark_id (PK), student_id (FK), subject, and score.
CREATE TABLE marks (
    mark_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    subject VARCHAR(30),
    score INT,
    CONSTRAINT fk_marks_student
        FOREIGN KEY (student_id) REFERENCES students(student_id)
);

-- 2. Add a column phone to students.
ALTER TABLE students
ADD COLUMN phone VARCHAR(10);

-- 3. Change phone to VARCHAR(15).
ALTER TABLE students
MODIFY COLUMN phone VARCHAR(15);

-- 4. Rename the column city to hometown.
ALTER TABLE students
RENAME COLUMN city TO hometown;

-- 5. Create an index on name.
CREATE INDEX idx_students_name ON students(name);

-- 6. Create a copy of the structure as students_backup, then drop that table.
CREATE TABLE students_backup LIKE students;
DROP TABLE students_backup;


-- ============================================================================
-- MySQL Practice Tasks: DML (Data Manipulation Language)
-- Database: school_db
-- ============================================================================

USE school_db;

-- Task 1: Insert data [5 marks]
-- 1. Insert one student using a single INSERT.
INSERT INTO students (name, email, age, hometown, admission_date, phone)
VALUES ('Aarav Sharma', 'aarav.sharma@school.edu', 14, 'Delhi', '2024-04-01', '9876543210');

-- 2. Insert 5 more students with a single multi-row INSERT.
INSERT INTO students (name, email, age, hometown, admission_date, phone)
VALUES
    ('Ananya Verma', 'ananya.verma@school.edu', 12, 'Mumbai', '2024-04-02', '9876543211'),
    ('Rohan Gupta',  'rohan.gupta@school.edu',  15, 'Delhi',  '2024-04-03', '9876543212'),
    ('Priya Singh',  'priya.singh@school.edu',  11, 'Noida',  '2024-04-04', '9876543213'),
    ('Kabir Das',    'kabir.das@school.edu',    16, 'Pune',   '2024-04-05', '9876543214'), -- Kept without marks for LEFT JOIN
    ('Meera Nair',   'meera.nair@school.edu',   13, 'Delhi',  '2024-04-06', '9876543215');

-- 3. Insert at least 10 rows into marks covering 3 subjects (Maths, Science, English).
INSERT INTO marks (student_id, subject, score)
VALUES
    (1, 'Maths',   85),
    (1, 'Science', 78),
    (1, 'English', 65),
    (2, 'Maths',   72),
    (2, 'Science', 80),
    (2, 'English', 55),
    (3, 'Maths',   60),
    (3, 'Science', 64),
    (3, 'English', 50),
    (4, 'Maths',   90),
    (4, 'Science', 15), -- Score below 20 (will be deleted in Task 2.5)
    (4, 'English', 58),
    (6, 'Maths',   68); -- Linked to student_id = 6 (tests FK restriction in Task 2.4)

-- 4. Try inserting a duplicate email and note the error you get.
INSERT INTO students (name, email, age, admission_date)
VALUES ('Duplicate Test', 'aarav.sharma@school.edu', 13, '2024-05-01');
-- OBSERVED ERROR:
-- ERROR 1062 (23000): Duplicate entry 'aarav.sharma@school.edu' for key 'students.email'


-- Task 2: Update and delete [5 marks]
-- 1. Increase every score in Maths by 5 marks.
UPDATE marks
SET score = score + 5
WHERE subject = 'Maths';

-- 2. Change the hometown of the student with student_id = 2 to 'Noida'.
UPDATE students
SET hometown = 'Noida'
WHERE student_id = 2;

-- 3. Update the age of all students from 'Delhi' by adding 1.
UPDATE students
SET age = age + 1
WHERE hometown = 'Delhi';

-- 4. Delete the student with student_id = 6. What happens to their marks? (Think about foreign keys.)
DELETE FROM students
WHERE student_id = 6;
-- WHAT HAPPENS TO THEIR MARKS:
-- Because `marks.student_id` is a FOREIGN KEY referencing `students(student_id)` with the
-- default referential action (RESTRICT / NO ACTION), MySQL prevents deleting the parent row
-- while matching child rows exist in `marks`:
-- ERROR 1451 (23000): Cannot delete or update a parent row: a foreign key constraint fails
-- (`school_db`.`marks`, CONSTRAINT `fk_marks_student` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`))
-- Therefore, neither the student nor their marks are deleted unless child rows are deleted first
-- or ON DELETE CASCADE was specified on the foreign key.

-- 5. Delete all marks below 20.
DELETE FROM marks
WHERE score < 20;


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


-- ============================================================================
-- MySQL Practice Tasks: TCL (Transaction Control Language)
-- Database: school_db
-- ============================================================================

USE school_db;

-- Task 1: Commit and rollback [5 marks]
-- 1. Run SET autocommit = 0;
SET autocommit = 0;

-- 2. Start a transaction, update the score of one student to 100, then run SELECT to verify it.
START TRANSACTION;
UPDATE marks
SET score = 100
WHERE mark_id = 1;

SELECT * FROM marks WHERE mark_id = 1; -- Shows score = 100 inside current session

-- 3. Run ROLLBACK and check the score again. What changed?
ROLLBACK;
SELECT * FROM marks WHERE mark_id = 1;
-- WHAT CHANGED:
-- The score reverted back to its original value before the transaction started (90),
-- because ROLLBACK undid the uncommitted UPDATE operation.

-- 4. Repeat the same steps, but this time run COMMIT. Check the score again.
START TRANSACTION;
UPDATE marks
SET score = 100
WHERE mark_id = 1;

COMMIT;
SELECT * FROM marks WHERE mark_id = 1; -- Score is permanently saved as 100


-- Task 2: Savepoints [5 marks]
-- 1. Start a transaction.
START TRANSACTION;

-- 2. Insert a new student, then create SAVEPOINT s1.
INSERT INTO students (name, email, age, hometown, admission_date, phone)
VALUES ('Dev Patel', 'dev.patel@school.edu', 14, 'Delhi', '2024-04-10', '9876543216');
SAVEPOINT s1;

-- 3. Insert a second student, then create SAVEPOINT s2.
INSERT INTO students (name, email, age, hometown, admission_date, phone)
VALUES ('Isha Malhotra', 'isha.malhotra@school.edu', 13, 'Noida', '2024-04-11', '9876543217');
SAVEPOINT s2;

-- 4. Delete one mark record.
DELETE FROM marks
WHERE mark_id = 2;

-- 5. Roll back to s2, then check which changes remain.
ROLLBACK TO SAVEPOINT s2;
SELECT * FROM students;                -- Both 'Dev Patel' and 'Isha Malhotra' remain
SELECT * FROM marks WHERE mark_id = 2; -- Deleted mark_id = 2 is restored

-- 6. Roll back to s1 and check again, then COMMIT.
ROLLBACK TO SAVEPOINT s1;
SELECT * FROM students;                -- Only 'Dev Patel' remains; 'Isha Malhotra' is rolled back
COMMIT;

-- Restore default autocommit mode
SET autocommit = 1;

-- 7. Think: Why does TRUNCATE or DROP inside a transaction not roll back?
-- ANSWER:
-- TRUNCATE and DROP are DDL (Data Definition Language) statements, whereas transactions
-- govern DML (Data Manipulation Language) statements. In MySQL, executing any DDL
-- command triggers an implicit COMMIT both before and after the statement runs,
-- immediately ending the active transaction and making the schema/table change permanent.


-- ============================================================================
-- MySQL Practice Tasks: DCL (Data Control Language)
-- Note: Execute with an administrative account (e.g., root).
-- ============================================================================

-- Task 1: Create users and grant access [2 marks]
-- 1. Create a user 'teacher1'@'localhost' with a password.
CREATE USER 'teacher1'@'localhost' IDENTIFIED BY 'Teacher@123';

-- 2. Grant SELECT, INSERT, UPDATE ON school_db.marks to this user.
GRANT SELECT, INSERT, UPDATE ON school_db.marks TO 'teacher1'@'localhost';

-- 3. Show the privileges using SHOW GRANTS.
SHOW GRANTS FOR 'teacher1'@'localhost';


-- Task 2: Revoke and roles [3 marks]
-- 1. Revoke the UPDATE privilege from teacher1.
REVOKE UPDATE ON school_db.marks FROM 'teacher1'@'localhost';
SHOW GRANTS FOR 'teacher1'@'localhost';

-- 2. Create a role viewer that can only SELECT on all tables in school_db.
CREATE ROLE 'viewer';
GRANT SELECT ON school_db.* TO 'viewer';

-- 3. Create a user guest1, assign the viewer role, and list the grants.
CREATE USER 'guest1'@'localhost' IDENTIFIED BY 'Guest@123';
GRANT 'viewer' TO 'guest1'@'localhost';
SET DEFAULT ROLE 'viewer' TO 'guest1'@'localhost';
SHOW GRANTS FOR 'guest1'@'localhost' USING 'viewer';

-- 4. Drop both users (and clean up the role).
DROP USER 'teacher1'@'localhost', 'guest1'@'localhost';
DROP ROLE 'viewer';


-- ============================================================================
-- MySQL Practice Tasks: Data Types and Constraints
-- Database: school_db
-- ============================================================================

USE school_db;

-- Task 1: Choose the right type [5 marks]
-- Create a table library_books with optimal data types for every column:
CREATE TABLE library_books (
    book_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(150) NOT NULL,                        -- Variable-length book title
    isbn CHAR(13) UNIQUE,                               -- Fixed 13-character string preserves leading zeros
    price DECIMAL(8, 2) CHECK (price >= 0),             -- Exact 2-decimal precision for currency
    copies INT UNSIGNED DEFAULT 1,                      -- Non-negative integer count of copies
    is_available BOOLEAN DEFAULT TRUE,                  -- Boolean (TINYINT(1)): TRUE (yes) / FALSE (no)
    category ENUM('Fiction', 'Science', 'History'),     -- Restricts values to the 3 allowed categories
    published_year YEAR,                                -- Dedicated 4-digit year type
    description TEXT                                    -- Long variable-length description
);


-- Task 2: Constraints [5 marks]
-- 1. Create a table teachers with PK, UNIQUE email, NOT NULL name, CHECK salary > 0, and DEFAULT today's date.
CREATE TABLE teachers (
    teacher_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE,
    salary DECIMAL(10, 2) CHECK (salary > 0),
    joining_date DATE DEFAULT (CURRENT_DATE)
);

-- Insert a baseline valid teacher row so the UNIQUE email test below collides with an existing email
INSERT INTO teachers (name, email, salary)
VALUES ('Ravi Kumar', 'ravi.kumar@school.edu', 55000.00);

-- 2. Write 3 INSERT statements that each break a different constraint, and note the error for each:

-- Violation A: Breaks the NOT NULL constraint on `name`
INSERT INTO teachers (name, email, salary)
VALUES (NULL, 'anita.desai@school.edu', 48000.00);
-- OBSERVED ERROR:
-- ERROR 1048 (23000): Column 'name' cannot be null

-- Violation B: Breaks the CHECK constraint (`salary > 0`)
INSERT INTO teachers (name, email, salary)
VALUES ('Suresh Raina', 'suresh.raina@school.edu', -5000.00);
-- OBSERVED ERROR:
-- ERROR 3819 (HY000): Check constraint 'teachers_chk_1' is violated.

-- Violation C: Breaks the UNIQUE constraint on `email`
INSERT INTO teachers (name, email, salary)
VALUES ('Deepak Verma', 'ravi.kumar@school.edu', 52000.00);
-- OBSERVED ERROR:
-- ERROR 1062 (23000): Duplicate entry 'ravi.kumar@school.edu' for key 'teachers.email'
