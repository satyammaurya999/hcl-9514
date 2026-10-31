import java.util.LinkedList;
//public class Linkedlist {
//    public static void main(String[] args) {
//        LinkedList<Integer> list = new LinkedList<>();
//        list.add(10);
//        list.add(20);
//        list.add(30);
//        System.out.println(list);
//    }
//}
public class Linkedlist {
    public static void main(String[] args) {

        LinkedList<Integer> list = new LinkedList<>();

        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        list.add(50);

        // get(i) -> O(n)
        // Loop n times -> O(n)
        // Total -> O(n²)

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }
}