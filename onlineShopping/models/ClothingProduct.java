//clothing product
package onlineShopping.models;

public class ClothingProduct extends Product {
    private String size;

    public ClothingProduct(String name, double price, String size) {
        super(name, price);
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    @Override
    public String getCategory() {
        return "Clothing";
    }
}
