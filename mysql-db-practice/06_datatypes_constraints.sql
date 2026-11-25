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
