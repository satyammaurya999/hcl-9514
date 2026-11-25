# MySQL Practice Tasks (`school_db`)

Complete solutions for the **MySQL Practice Tasks** covering **DDL, DML, DQL, TCL, DCL, Data Types, and Constraints** using the `school_db` database.

---

## Repository Structure

```text
mysql-school-db-practice/
├── README.md                      # Documentation, schema summary, observed errors & conceptual notes
├── 01_ddl.sql                     # Database creation, tables, column alterations, index, backup/drop
├── 02_dml.sql                     # Single/multi-row inserts, updates, deletes, constraint tests
├── 03_dql.sql                     # Filtering, sorting, aggregates, HAVING, joins, subquery, RANK()
├── 04_tcl.sql                     # Transactions, AUTOCOMMIT, COMMIT, ROLLBACK, SAVEPOINTs
├── 05_dcl.sql                     # User creation, GRANT, REVOKE, ROLE assignment, cleanup
├── 06_datatypes_constraints.sql   # library_books data types & teachers constraint violations
└── school_db_all_in_one.sql       # Consolidated script containing all tasks in sequence
```

---

## How to Run

1. Open MySQL CLI or MySQL Workbench as an administrative user (e.g., `root`):
   ```bash
   mysql -u root -p
   ```
2. Execute the scripts in order (`01_ddl.sql` through `06_datatypes_constraints.sql`):
   ```sql
   SOURCE 01_ddl.sql;
   SOURCE 02_dml.sql;
   SOURCE 03_dql.sql;
   SOURCE 04_tcl.sql;
   SOURCE 05_dcl.sql;
   SOURCE 06_datatypes_constraints.sql;
   ```
   > **Note:** `02_dml.sql` and `06_datatypes_constraints.sql` include intentional constraint-violation `INSERT`/`DELETE` queries required by the assignment. If running non-interactively via CLI, pass `--force` (`mysql -u root -p --force < 02_dml.sql`) so execution continues past the expected error demonstrations.

---

## Observed Errors & Conceptual Answers

### 1. DML — Duplicate Email Insertion (Task 1.4)
* **Query:** Inserting a second student with `'aarav.sharma@school.edu'`.
* **Observed Error:**
  ```text
  ERROR 1062 (23000): Duplicate entry 'aarav.sharma@school.edu' for key 'students.email'
  ```

### 2. DML — Deleting `student_id = 6` & Foreign Key Behavior (Task 2.4)
* **Observation:** Student `student_id = 6` (`Meera Nair`) has an associated record in `marks`. Because `marks.student_id` is a `FOREIGN KEY` referencing `students(student_id)` without `ON DELETE CASCADE` (MySQL defaults to `RESTRICT` / `NO ACTION`), MySQL blocks the deletion to prevent orphaned rows:
  ```text
  ERROR 1451 (23000): Cannot delete or update a parent row: a foreign key constraint fails
  (`school_db`.`marks`, CONSTRAINT `fk_marks_student` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`))
  ```
* **Resolution:** Either delete the child rows in `marks` for `student_id = 6` first, or define the foreign key with `ON DELETE CASCADE`.

### 3. TCL — `ROLLBACK` vs. `COMMIT` (Task 1.3 & 1.4)
* After setting `autocommit = 0` and updating `score = 100` inside `START TRANSACTION`, `SELECT` within the same session shows `100` (uncommitted state).
* Running `ROLLBACK` reverts the score back to `90`.
* Repeating the update and running `COMMIT` permanently writes `100` to disk.

### 4. TCL — Savepoints & Why `TRUNCATE`/`DROP` Cannot Roll Back (Task 2.5–2.7)
* **`ROLLBACK TO SAVEPOINT s2`:** Undoes the `DELETE` on `marks`, while keeping both inserted students (`Dev Patel` and `Isha Malhotra`).
* **`ROLLBACK TO SAVEPOINT s1`:** Undoes the second student (`Isha Malhotra`), leaving only the first student (`Dev Patel`), which is persisted upon `COMMIT`.
* **Why `TRUNCATE` or `DROP` does not roll back:** `TRUNCATE` and `DROP` are **DDL** commands, not DML. In MySQL, DDL statements issue an **implicit `COMMIT`** immediately before and after executing, terminating any active transaction and making the operation permanent.

### 5. Data Types & Constraints — `teachers` Constraint Violations (Task 2.2)
| Constraint Tested | Invalid Input | Observed MySQL Error |
| :--- | :--- | :--- |
| **`NOT NULL`** (`name`) | `name = NULL` | `ERROR 1048 (23000): Column 'name' cannot be null` |
| **`CHECK`** (`salary > 0`) | `salary = -5000.00` | `ERROR 3819 (HY000): Check constraint 'teachers_chk_1' is violated.` |
| **`UNIQUE`** (`email`) | Duplicate `email` | `ERROR 1062 (23000): Duplicate entry 'ravi.kumar@school.edu' for key 'teachers.email'` |
