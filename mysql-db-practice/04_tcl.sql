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
