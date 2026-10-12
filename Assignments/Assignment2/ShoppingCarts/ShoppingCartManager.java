import java.util.HashMap;
import java.util.Map;

public class ShoppingCartManager {

    private final Map<Integer, ShoppingCart> userCarts;

    public ShoppingCartManager() {
        userCarts = new HashMap<>();
    }

    // Get existing cart or create a new cart
    public synchronized ShoppingCart getCart(int userId) {

        if (userId <= 0) {
            throw new IllegalArgumentException(
                    "User ID must be positive."
            );
        }

        return userCarts.computeIfAbsent(
                userId,
                id -> new ShoppingCart()
        );
    }

    public synchronized void removeUserCart(int userId) {
        userCarts.remove(userId);
    }
}