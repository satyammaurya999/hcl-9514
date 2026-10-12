import java.util.function.Consumer;

public class ConsumerInterfaces {
    public static void main(String[] args) {

        Consumer<String> printName = name -> System.out.println(name);

        printName.accept("Satyam");
    }
}