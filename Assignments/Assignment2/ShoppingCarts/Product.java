public abstract class Product {

    private final int productId;
    private String productName;
    private double price;
    private int quantity;

    public Product(int productId, String productName, double price, int quantity) {

        if (productId <= 0) {
            throw new IllegalArgumentException("Product ID must be positive.");
        }

        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty.");
        }

        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }

        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setProductName(String productName) {
        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty.");
        }

        this.productName = productName;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }

        this.price = price;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }

        this.quantity = quantity;
    }

    public abstract String getCategory();

    public double getTotalPrice() {
        return price * quantity;
    }

    @Override
    public String toString() {
        return "Product ID: " + productId +
                " | Name: " + productName +
                " | Category: " + getCategory() +
                " | Price: Rs. " + price +
                " | Quantity: " + quantity +
                " | Total: Rs. " + getTotalPrice();
    }
}