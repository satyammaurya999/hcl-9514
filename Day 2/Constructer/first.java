public class first {

    // Instance variable
    String name;
    int age;
    // Constructor
    // Constructor ka naam class ke naam jaisa hi hota hai
    first(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static void main(String[] args) {

        // Object create karte hi constructor call hoga
        first obj = new first("Satyam", 20);

        // Values print karna
        System.out.println("Name: " + obj.name);
        System.out.println("Age: " + obj.age);
    }
}