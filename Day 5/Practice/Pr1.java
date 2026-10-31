import java.util.*;

public class Pr1 {
    public static void main(String[] args) {

        ArrayList<Employee1>arr = new ArrayList<>();
        arr.add(new Employee1(20, 201, "satyam"));
        arr.add(new Employee1(22, 204, "shivam"));
        arr.add(new Employee1(50, 206, "akash"));
        arr.add(new Employee1(30, 221, "vishal"));
        arr.add(new Employee1(60, 291, "yash"));
        arr.sort(Comparator.comparingInt(e -> e.id));
        System.out.println("Ascending Order");
        for (Employee1 e : arr) {
            System.out.println(e.id + " " + e.name + " " + e.age);
        }
        System.out.println("Descending Order");
//        arr.sort(Comparator.comparingInt((Employee e) -> e.id).reversed());
//

        for (Employee1 e : arr) {
            System.out.println(e.id + " " + e.name + " " + e.age);
        }
        System.out.println("Traverse Using Iterator");
        Iterator<Employee1> itr = arr.iterator();
        while(itr.hasNext()){
            Employee1 e=itr.next();
            System.out.println(
                    e.id + " " + e.name + " " + e.age
            );
        }
        System.out.println("Traverse Using ListIterator");
        ListIterator<Employee1> litr = arr.listIterator();

        while (litr.hasNext()) {
            Employee1 e = litr.next();
            System.out.println(
                    e.id + " " + e.name + " " + e.age
            );
        }
        class MyComparator implements Comparator<Employee1> {
            @Override
            public int compare(Employee1 s1, Employee1 s2) {
                return s1.id-s2.id;
            }
        }
        Collections.sort(arr, new MyComparator());

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
