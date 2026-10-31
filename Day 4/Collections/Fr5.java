import java.util.*;

public class Fr5 {
    public static void main(String[] args) {

        Queue<Integer> q = new LinkedList<>();
        Queue<Integer> dq = new ArrayDeque<>();

        // Add elements
        q.offer(10);
        q.offer(20);
        q.offer(30);
        q.offer(40);

        System.out.println(q);  // [10, 20, 30, 40]

        // Remove element from front
        System.out.println(q.poll());  // 10

        // See front element
        System.out.println(q.peek());
        // 20
        q.offer(50);

        System.out.println(q);
        // [20, 30, 40]
    }
}