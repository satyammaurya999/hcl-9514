public class ElectronicProduct extends Product {

    private String brand;
    private int warrantyInMonths;

    public ElectronicProduct(int productId,
                             String productName,
                             double price,
                             int quantity,
                             String brand,
                             int warrantyInMonths) {

        super(productId, productName, price, quantity);

        if (warrantyInMonths < 0) {
            throw new IllegalArgumentException(
                    "Warranty cannot be negative."
            );
        }

        this.brand = brand;
        this.warrantyInMonths = warrantyInMonths;
    }

    public String getBrand() {
        return brand;
    }

    public int getWarrantyInMonths() {
        return warrantyInMonths;
    }

    @Override
    public String getCategory() {
        return "Electronics";
    }

    @Override
    public String toString() {
        return super.toString() +
                " | Brand: " + brand +
                " | Warranty: " + warrantyInMonths + " months";
    }
}