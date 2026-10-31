import java.util.HashMap;

public class Fr4 {
    public static void main(String[] args) {

        // HashMap<Key, Value>
        HashMap<Integer, String> map = new HashMap<>();

        // Adding elements
        map.put(101, "Satyam");
        map.put(102, "Rahul");
        map.put(103, "Aman");

        // Printing HashMap
        System.out.println(map);

        // Get value using key
        System.out.println(map.get(101));

        // Check if key exists
        System.out.println(map.containsKey(102));

        // Check if value exists
        System.out.println(map.containsValue("Aman"));

        // Update value
        map.put(101, "Satyam Maurya");

        // Remove using key
        map.remove(103);

        // Size
        System.out.println(map.size());

        // Traverse HashMap
        for (Integer key : map.keySet()) {
            System.out.println(key + " -> " + map.get(key));
        }
    }
}