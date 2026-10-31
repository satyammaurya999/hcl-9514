import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ShoppingCart {

    private final List<Product> products;

    public ShoppingCart() {
        products = new ArrayList<>();
    }

    // Add product to cart
    public synchronized void addProduct(Product product) {

        if (product == null) {
            throw new IllegalArgumentException(
                    "Product cannot be null."
            );
        }

        // If product already exists, increase quantity
        for (Product existingProduct : products) {

            if (existingProduct.getProductId()
                    == product.getProductId()) {

                existingProduct.setQuantity(
                        existingProduct.getQuantity()
                                + product.getQuantity()
                );

                return;
            }
        }

        products.add(product);
    }

    // Remove product from cart
    public synchronized boolean removeProduct(int productId) {

        for (Product product : products) {

            if (product.getProductId() == productId) {

                products.remove(product);
                return true;
            }
        }

        return false;
    }

    // Update product quantity
    public synchronized boolean updateProductQuantity(
            int productId,
            int newQuantity) {

        if (newQuantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than 0."
            );
        }

        for (Product product : products) {

            if (product.getProductId() == productId) {

                product.setQuantity(newQuantity);
                return true;
            }
        }

        return false;
    }

    // Show products in sorted order
    public synchronized void showCart() {

        if (products.isEmpty()) {

            System.out.println("\nCart is empty.");
            return;
        }

        List<Product> sortedProducts =
                new ArrayList<>(products);

        // Sort by product name
        sortedProducts.sort(
                Comparator.comparing(
                        Product::getProductName,
                        String.CASE_INSENSITIVE_ORDER
                )
        );

        System.out.println("\n========== SHOPPING CART ==========");

        for (Product product : sortedProducts) {
            System.out.println(product);
        }

        System.out.println("====================================");
        System.out.println(
                "Cart Total: Rs. " + getCartTotal()
        );
    }

    public synchronized double getCartTotal() {

        double total = 0;

        for (Product product : products) {
            total += product.getTotalPrice();
        }

        return total;
    }

    public synchronized boolean isEmpty() {
        return products.isEmpty();
    }
}