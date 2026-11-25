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
