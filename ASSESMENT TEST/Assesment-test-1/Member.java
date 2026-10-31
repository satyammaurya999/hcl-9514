

import java.util.ArrayList;
import java.util.List;

public class Member {

    private static final int FIRST_ID = 1;
    private static int nextId = FIRST_ID;

    private final int id;
    private String name;
    private String email;

    private final List<LibraryItem> borrowedItems =
            new ArrayList<>();

    // Constructor 1: automatically generated ID.
    public Member(String name, String email) {
        this(nextId, name, email);
    }

    // Constructor 2: explicitly provided ID.
    public Member(int id, String name, String email) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Member ID must be positive."
            );
        }

        this.id = id;
        setName(name);
        setEmail(email);

        nextId = Math.max(nextId, id + 1);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public final void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Member name must not be blank."
            );
        }

        this.name = name.trim();
    }

    public String getEmail() {
        return email;
    }

    public final void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException(
                    "Email must contain @."
            );
        }

        this.email = email.trim();
    }

    // Do not expose the internal mutable list.
    public List<LibraryItem> getBorrowedItems() {
        return List.copyOf(borrowedItems);
    }

    public int getBorrowedItemCount() {
        return borrowedItems.size();
    }

    public void addBorrowedItem(LibraryItem item) {
        if (item == null) {
            throw new IllegalArgumentException(
                    "Borrowed item cannot be null."
            );
        }

        if (!borrowedItems.contains(item)) {
            borrowedItems.add(item);
        }
    }

    public boolean removeBorrowedItem(LibraryItem item) {
        return borrowedItems.remove(item);
    }

    @Override
    public String toString() {
        return "Member[id=" + id
                + ", name='" + name + '\''
                + ", email='" + email + '\''
                + ", borrowedItems=" + borrowedItems.size()
                + "]";
    }
}