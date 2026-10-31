@FunctionalInterface
interface Calculate {
    int calculate(int a, int b);
}

public class Calculator {
    public static void main(String[] args) {
        Calculate add = (a, b) -> a + b;
        Calculate subtract = (a, b) -> a - b;
        Calculate multiply = (a, b) -> a * b;
        Calculate divide = (a, b) -> a / b;

        System.out.println("Addition: " + add.calculate(10, 5));
        System.out.println("Subtraction: " + subtract.calculate(10, 5));
        System.out.println("Multiplication: " + multiply.calculate(10, 5));
        System.out.println("Division: " + divide.calculate(10, 5));



    }
}