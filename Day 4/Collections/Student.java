import java.util.*;

public class Student {

    int age;
    String name;
    int rollno;

    // Constructor
    public Student(String name, int age, int rollno) {
        this.name = name;
        this.age = age;
        this.rollno = rollno;
    }

    public static void main(String[] args) {

        // Creating ArrayList of Student objects
        ArrayList<Student> arr = new ArrayList<>();

        // Adding Student objects
        arr.add(new Student("Satyam", 20, 201));
        arr.add(new Student("Shivam", 22, 203));
        arr.add(new Student("Shiva", 23, 204));
        arr.add(new Student("Vishal", 24, 205));
        arr.add(new Student("Subodh", 26, 206));

        // Sort students by age in descending order
        Collections.sort(arr, (a, b) -> {

            if (a.age < b.age) {
                return 1;
            }
            else if (a.age > b.age) {
                return -1;
            }
            else {
                return 0;
            }
        });

        // Print sorted students
        for (Student s : arr) {
            System.out.println(
                    "Name: " + s.name +
                            ", Age: " + s.age +
                            ", Roll No: " + s.rollno
            );
        }
    }
}