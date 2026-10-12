import java.util.Scanner;

public class Main1 {

    private static final Scanner scanner =
            new Scanner(System.in);

    private static final ShoppingCartManager cartManager =
            new ShoppingCartManager();

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("     ONLINE SHOPPING CART");
        System.out.println("=================================");

        System.out.print("Enter User ID: ");

        int userId = readPositiveInteger();

        ShoppingCart cart = cartManager.getCart(userId);

        boolean running = true;

        while (running) {

            showMenu();

            int choice = readPositiveInteger();

            try {

                switch (choice) {

                    case 1:
                        addProduct(cart);
                        break;

                    case 2:
                        removeProduct(cart);
                        break;

                    case 3:
                        updateProduct(cart);
                        break;

                    case 4:
                        cart.showCart();
                        break;

                    case 5:
                        System.out.println(
                                "Thank you for shopping!"
                        );
                        running = false;
                        break;

                    default:
                        System.out.println(
                                "Invalid choice. Please try again."
                        );
                }

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Error: " + e.getMessage()
                );
            }
        }

        scanner.close();
    }

    private static void showMenu() {

        System.out.println("\n========== MENU ==========");
        System.out.println("1. Add Product");
        System.out.println("2. Remove Product");
        System.out.println("3. Update Product");
        System.out.println("4. Show Cart");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addProduct(ShoppingCart cart) {

        System.out.println("\nSelect Product Type:");
        System.out.println("1. Clothes");
        System.out.println("2. Electronics");
        System.out.println("3. Toys");

        System.out.print("Enter choice: ");

        int type = readPositiveInteger();

        System.out.print("Enter Product ID: ");
        int productId = readPositiveInteger();

        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Price: ");
        double price = readPositiveDouble();

        System.out.print("Enter Quantity: ");
        int quantity = readPositiveInteger();

        Product product;

        switch (type) {

            case 1:

                System.out.print("Enter Size: ");
                String size = scanner.nextLine();

                System.out.print("Enter Material: ");
                String material = scanner.nextLine();

                product = new ClothesProduct(
                        productId,
                        name,
                        price,
                        quantity,
                        size,
                        material
                );

                break;

            case 2:

                System.out.print("Enter Brand: ");
                String brand = scanner.nextLine();

                System.out.print(
                        "Enter Warranty (months): "
                );

                int warranty = readPositiveInteger();

                product = new ElectronicProduct(
                        productId,
                        name,
                        price,
                        quantity,
                        brand,
                        warranty
                );

                break;

            case 3:

                System.out.print("Enter Age Group: ");
                String ageGroup = scanner.nextLine();

                product = new ToyProduct(
                        productId,
                        name,
                        price,
                        quantity,
                        ageGroup
                );

                break;

            default:
                System.out.println(
                        "Invalid product type."
                );
                return;
        }

        cart.addProduct(product);

        System.out.println(
                "Product added successfully."
        );
    }

    private static void removeProduct(ShoppingCart cart) {

        System.out.print("Enter Product ID to remove: ");

        int productId = readPositiveInteger();

        boolean removed =
                cart.removeProduct(productId);

        if (removed) {
            System.out.println(
                    "Product removed successfully."
            );
        } else {
            System.out.println(
                    "Product not found in cart."
            );
        }
    }

    private static void updateProduct(ShoppingCart cart) {

        System.out.print("Enter Product ID: ");

        int productId = readPositiveInteger();

        System.out.print("Enter New Quantity: ");

        int quantity = readPositiveInteger();

        boolean updated =
                cart.updateProductQuantity(
                        productId,
                        quantity
                );

        if (updated) {
            System.out.println(
                    "Product updated successfully."
            );
        } else {
            System.out.println(
                    "Product not found in cart."
            );
        }
    }

    private static int readPositiveInteger() {

        while (true) {

            try {

                int value =
                        Integer.parseInt(
                                scanner.nextLine()
                        );

                if (value <= 0) {
                    System.out.print(
                            "Enter a positive number: "
                    );
                    continue;
                }

                return value;

            } catch (NumberFormatException e) {

                System.out.print(
                        "Invalid input. Enter a number: "
                );
            }
        }
    }

    private static double readPositiveDouble() {

        while (true) {

            try {

                double value =
                        Double.parseDouble(
                                scanner.nextLine()
                        );

                if (value < 0) {
                    System.out.print(
                            "Price cannot be negative. Enter again: "
                    );
                    continue;
                }

                return value;

            } catch (NumberFormatException e) {

                System.out.print(
                        "Invalid price. Enter again: "
                );
            }
        }
    }
}