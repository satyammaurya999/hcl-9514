# HCL Tech Training - Assessment 2

# NAME-SATYAM MAURYA
# BRANCH-CSE
# ROLL NUMBER-2300320100225

## Library Management System


### Student Details
- Name: YOUR_NAME
- Roll Number: YOUR_ROLL_NUMBER
- Batch: YOUR_BATCH

### Overview
A console-based Library Management System implemented in Core Java.
It manages Books, Magazines, DVDs and Members.

### Features
- Add books, magazines and DVDs
- Register library members
- Issue and return items
- Calculate late fees
- Manage item waitlists
- Search and sort items
- Export library reports
- View item statistics
- Remove old available items

### Design
- Borrowable is an interface.
- LibraryItem is an abstract class implementing Borrowable
  and Comparable<LibraryItem>.
- Book, Magazine and DVD extend LibraryItem.
- LibraryService contains the business logic.
- Repository<T, ID> defines generic repository operations.
- InMemoryRepository<T, ID> implements Repository.
- Custom exceptions extend LibraryException.
- SearchCache maintains the five most recently used queries.

### Compile and Run (Windows CMD)
From the repository root:

    cd Assignment
    dir /s /b src\*.java > sources.txt
    javac -d out @sources.txt
    java -cp out com.hcl.library.Main

### LRU Cache and Memory Leaks
The search cache retains only the five most recently used queries.
Older entries are removed when the cache exceeds its capacity.
An unbounded state map can retain every query and its result objects.
This can cause unnecessary memory growth as searches accumulate.

### Test Cases
1. Successfully borrow an available item.
   Expected: Item status changes to Issued.

2. Borrow a fourth item when a member already has three.
   Expected: BorrowLimitExceededException message.

3. Borrow an item ID that does not exist.
   Expected: ItemNotFoundException message.

4. Attempt to borrow an already issued item and join the waitlist.
   Expected: Member is added to that item's waiting queue.

5. Return an item with a waitlisted member.
   Expected: Late fee is displayed and the next member is notified.

### Challenges and Learning
- Applied abstraction, inheritance, encapsulation and polymorphism.
- Practiced custom exceptions and input validation.
- Used Lists, Sets, Maps, Queues and comparators.
- Implemented a generic repository and bounded LRU cache.
- Learned resource-safe report export with try-with-resources.