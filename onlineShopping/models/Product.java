package onlineShopping.models;

public abstract class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    // Abstract method to be implemented by subclasses
    public abstract String getCategory();

    @Override
    public String toString() {
        return getCategory() + ": " + name + " ($" + price + ")";
    }
}


