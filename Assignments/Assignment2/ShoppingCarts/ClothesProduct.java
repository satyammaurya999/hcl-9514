public class ClothesProduct extends Product {

    private String size;
    private String material;

    public ClothesProduct(int productId,
                          String productName,
                          double price,
                          int quantity,
                          String size,
                          String material) {

        super(productId, productName, price, quantity);

        this.size = size;
        this.material = material;
    }

    public String getSize() {
        return size;
    }

    public String getMaterial() {
        return material;
    }

    @Override
    public String getCategory() {
        return "Clothes";
    }

    @Override
    public String toString() {
        return super.toString() +
                " | Size: " + size +
                " | Material: " + material;
    }
}