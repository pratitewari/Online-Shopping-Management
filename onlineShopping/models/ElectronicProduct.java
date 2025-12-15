package onlineShopping.models;

//electronic product 
public class ElectronicProduct extends Product {
    private int warrantyYears;

    public ElectronicProduct(String name, double price, int warrantyYears) {
        super(name, price);
        this.warrantyYears = warrantyYears;
    }

    public int getWarrantyYears() {
        return warrantyYears;
    }

    @Override
    public String getCategory() {
        return "Electronic";
    }
}
