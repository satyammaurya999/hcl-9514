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
