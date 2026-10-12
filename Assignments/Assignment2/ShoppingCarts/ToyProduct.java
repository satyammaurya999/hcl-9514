public class ToyProduct extends Product {

    private String ageGroup;

    public ToyProduct(int productId,
                      String productName,
                      double price,
                      int quantity,
                      String ageGroup) {

        super(productId, productName, price, quantity);

        this.ageGroup = ageGroup;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    @Override
    public String getCategory() {
        return "Toys";
    }

    @Override
    public String toString() {
        return super.toString() +
                " | Age Group: " + ageGroup;
    }
}