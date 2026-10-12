import java.util.function.Supplier;
public class SupplierInterfaces {
    public static void main(String[] args) {

        Supplier<String> name = () -> "Satyam";

        System.out.println(name.get());
    }
}
