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
