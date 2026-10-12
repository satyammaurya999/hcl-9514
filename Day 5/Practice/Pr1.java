import java.util.*;

public class Pr1 {
    public static void main(String[] args) {

        ArrayList<Employee>arr = new ArrayList<>();
        arr.add(new Employee(20, 201, "satyam"));
        arr.add(new Employee(22, 204, "shivam"));
        arr.add(new Employee(50, 206, "akash"));
        arr.add(new Employee(30, 221, "vishal"));
        arr.add(new Employee(60, 291, "yash"));
        arr.sort(Comparator.comparingInt(e -> e.id));
        System.out.println("Ascending Order");
        for (Employee e : arr) {
            System.out.println(e.id + " " + e.name + " " + e.age);
        }
        System.out.println("Descending Order");
        arr.sort(Comparator.comparingInt((Employee e) -> e.id).reversed());
        for (Employee e : arr) {
            System.out.println(e.id + " " + e.name + " " + e.age);
        }
        System.out.println("Traverse Using Iterator");
        Iterator<Employee> itr = arr.iterator();
        while(itr.hasNext()){
            Employee e=itr.next();
            System.out.println(
                    e.id + " " + e.name + " " + e.age
            );
        }
        System.out.println("Traverse Using ListIterator");
        ListIterator<Employee> litr = arr.listIterator();

        while (litr.hasNext()) {
            Employee e = litr.next();
            System.out.println(
                    e.id + " " + e.name + " " + e.age
            );
        }

    }
}

class Employee {
    int age;
    int id;
    String name;

    public Employee(int age, int id, String name) {
        this.age = age;
        this.id = id;
        this.name = name;
    }
}
