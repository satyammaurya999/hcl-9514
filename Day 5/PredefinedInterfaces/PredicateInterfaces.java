import java.util.function.Predicate;

public class PredicateInterfaces {
    public static void main(String[] args) {

        Predicate<Integer> even = x -> x % 2 == 0;

        System.out.println(even.test(10));
        System.out.println(even.test(7));
    }
}