import java.util.ArrayList;
import java.util.Comparator;

public class Main {

    public static void main(String[] args) {

        ArrayList<Employee1> employees = new ArrayList<>();

        employees.add(
                new Employee1(
                        100,
                        "Rahul",
                        "Manager",
                        50000,
                        10000,
                        5000,
                        3000
                )
        );

        employees.add(
                new Employee1(
                        101,
                        "Amit",
                        "Engineer",
                        45000,
                        8000,
                        4000,
                        2500
                )
        );

        employees.add(
                new Employee1(
                        102,
                        "Priya",
                        "Admin",
                        40000,
                        7000,
                        3000,
                        2000
                )
        );

        employees.add(
                new Employee1(
                        103,
                        "Neha",
                        "Accountant",
                        50000,
                        10000,
                        4000,
                        2500
                )
        );

        BonusCalculator bonusCalculator = new BonusCalculator();

        // Sort employees in descending order of bonus
        employees.sort(
                Comparator.comparingDouble(
                        bonusCalculator::calculateBonus
                ).reversed()
        );

        // Display output
        for (Employee1 employee : employees) {

            double bonus = bonusCalculator.calculateBonus(employee);

            System.out.println("---------------------------------------------");
            System.out.println("Emp_id: " + employee.getEmpId());
            System.out.println("Name: " + employee.getName());
            System.out.println("Role: " + employee.getRole());
            System.out.println("Basic: Rs. " + employee.getBasic());
            System.out.println("HRA: Rs. " + employee.getHra());
            System.out.println("TA: Rs. " + employee.getTa());
            System.out.println(
                    "Special Allowance: Rs. "
                            + employee.getSpecialAllowance()
            );
            System.out.println("Bonus: Rs. " + bonus);
        }

        System.out.println("---------------------------------------------");
    }
}