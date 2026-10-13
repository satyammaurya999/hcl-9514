import java.util.*;
import java.util.stream.Collectors;
public class Filter {
    public static void main(String[] args) {
        List<Integer> l= Arrays.asList(1,2,3,4,5,6,7);
        List<Integer> result= l.stream().filter(n->n%2==0).sorted().toList();
        System.out.println(result);
    }

}
