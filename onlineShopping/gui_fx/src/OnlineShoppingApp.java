import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Abstract Product class
abstract class Product {
    protected String name;
    protected double price;
    protected int stock;

    public Product(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public abstract void displayInfo();

    public void reduceStock(int quantity) throws InsufficientStockException {
        if (quantity > stock) {
            throw new InsufficientStockException("Insufficient stock for " + name);
        }
        stock -= quantity;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }
}

// Electronics subclass
class Electronics extends Product {
    private String brand;

    public Electronics(String name, double price, int stock, String brand) {
        super(name, price, stock);
        this.brand = brand;
    }

    @Override
    public void displayInfo() {
        System.out.println("Electronics - Name: " + name + ", Price: " + price + ", Brand: " + brand + ", Stock: " + stock);
    }
}

// Grocery subclass
class Grocery extends Product {
    private double weight;

    public Grocery(String name, double price, int stock, double weight) {
        super(name, price, stock);
        this.weight = weight;
    }

    @Override
    public void displayInfo() {
        System.out.println("Grocery - Name: " + name + ", Price: " + price + ", Weight: " + weight + "kg, Stock: " + stock);
    }
}

// Clothes subclass
class Clothes extends Product {
    private String type; // Type of clothing (e.g., Shirt, Tshirt, Sweatshirt)

    public Clothes(String name, double price, int stock, String type) {
        super(name, price, stock);
        this.type = type;
    }

    @Override
    public void displayInfo() {
        System.out.println("Clothing - Name: " + name + ", Price: " + price + ", Type: " + type + ", Stock: " + stock);
    }
}

// Books subclass
class Books extends Product {
    private String genre; // Genre of the book (e.g., Fiction, Non-Fiction)

    public Books(String name, double price, int stock, String genre) {
        super(name, price, stock);
        this.genre = genre;
    }

    @Override
    public void displayInfo() {
        System.out.println("Book - Name: " + name + ", Price: " + price + ", Genre: " + genre + ", Stock: " + stock);
    }
}

// HomeFinds subclass
class HomeFinds extends Product {
    private String category; // Category of home find (e.g., Couch, Table, etc.)

    public HomeFinds(String name, double price, int stock, String category) {
        super(name, price, stock);
        this.category = category;
    }

    @Override
    public void displayInfo() {
        System.out.println("Home Find - Name: " + name + ", Price: " + price + ", Category: " + category + ", Stock: " + stock);
    }
}

// Custom exception for stock issues
class InsufficientStockException extends Exception {
    public InsufficientStockException(String message) {
        super(message);
    }
}

// Order class with generics
class Order<T extends Product> {
    private T product;
    private int quantity;

    public Order(T product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public double calculateTotal() {
        return product.getPrice() * quantity;
    }

    public void processOrder() throws InsufficientStockException {
        product.reduceStock(quantity);
    }

    public void displayOrderInfo() {
        System.out.println("Order for: ");
        product.displayInfo();
        System.out.println("Quantity: " + quantity);
        System.out.println("Total Price: " + calculateTotal());
    }
}

// ShoppingCart class to hold orders and process them with multithreading
class ShoppingCart {
    private List<Order<?>> orders = new ArrayList<>();

    public void addOrder(Order<?> order) {
        orders.add(order);
    }

    public void displayCart() {
        System.out.println("Shopping Cart:");
        for (Order<?> order : orders) {
            order.displayOrderInfo();
            System.out.println("--------------------");
        }
    }

    public void checkout() {
        ExecutorService executor = Executors.newFixedThreadPool(2); // Create a pool with 2 threads
        for (Order<?> order : orders) {
            executor.submit(new ProcessOrderTask(order));
        }
        executor.shutdown();
    }

    // Inner class to process each order in a separate thread
    private static class ProcessOrderTask implements Runnable {
        private final Order<?> order;

        public ProcessOrderTask(Order<?> order) {
            this.order = order;
        }

        @Override
        public void run() {
            try {
                System.out.println("Processing order in thread: " + Thread.currentThread().getName());
                order.processOrder();
                System.out.println("Order processed successfully!");
                order.displayOrderInfo();
            } catch (InsufficientStockException e) {
                System.out.println("Failed to process order: " + e.getMessage());
            }
        }
    }

    private void showTotalBill() {
        double totalBill = 0.0;
        for (Order<?> order : orders) {
            totalBill += order.calculateTotal(); // Add up the total of each order
        }
        System.out.println("Total Bill: " + totalBill);
    }
}

//JavaFX Application for UI
public class OnlineShoppingApp extends Application {
    private ShoppingCart cart = new ShoppingCart();
    private List<Product> products = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Online Shopping App");

        // Adding Electrnics products
        products.add(new Electronics("Laptop", 1200, 10, "Lenovo"));
        products.add(new Electronics("Mouse", 500, 20, "Dell"));
        products.add(new Electronics("Earphones", 300, 15, "Boat"));
        products.add(new Electronics("Camera", 800, 10, "Canon"));
        products.add(new Electronics("Smartwatch", 700, 30, "Noise"));
        products.add(new Electronics("Pendrive", 200, 40, "SanDisk"));

        //Adding Grocery products
        products.add(new Grocery("Apples", 2.5, 50, 1.0));
        products.add(new Grocery("Instant Coffee", 5.0, 30, 0.5));
        products.add(new Grocery("Bell Peppers", 3.0, 40, 0.3)); 
        products.add(new Grocery("Chocolates", 7.0, 20, 0.2));
        products.add(new Grocery("Mustard Oil", 10.0, 15, 1.0));

        // Adding Clothes products
        products.add(new Clothes("Shirt", 25.0, 30, "Shirts"));
        products.add(new Clothes("Tshirt", 15.0, 50, "Tshirts"));
        products.add(new Clothes("Sweatshirt", 40.0, 20, "Sweatshirts"));
        products.add(new Clothes("Hoodie", 45.0, 15, "Hoodies"));
        products.add(new Clothes("Bottoms", 30.0, 25, "Bottoms"));

        // Adding Books products
        products.add(new Books("Fiction Novel", 12.0, 20, "Fiction"));
        products.add(new Books("Non-Fiction Book", 18.0, 10, "Non-Fiction"));
        products.add(new Books("Biography", 22.0, 12, "Biographies"));
        products.add(new Books("Historical Findings", 25.0, 8, "Historical Findings"));
        products.add(new Books("Children's Book", 10.0, 50, "Children's books"));

        // Adding HomeFinds products
        products.add(new HomeFinds("Couch", 300.0, 5, "Couches"));
        products.add(new HomeFinds("Dining Table", 150.0, 10, "Tables and Chairs"));
        products.add(new HomeFinds("Kitchenware Set", 80.0, 15, "Kitchenware"));
        products.add(new HomeFinds("Curtains", 25.0, 40, "Curtains"));
        products.add(new HomeFinds("Cleaning Supplies", 10.0, 60, "Cleaning supplies"));
        
        // UI components
        Label productLabel = new Label("Select Product:");
        ComboBox<String> productComboBox = new ComboBox<>();
        for (Product product : products) {
            productComboBox.getItems().add(product.name);
        }

        Label quantityLabel = new Label("Quantity:");
        TextField quantityField = new TextField();

        Button addButton = new Button("Add to Cart");
        Button checkoutButton = new Button("Checkout");

        addButton.setOnAction(e -> {
            String selectedProduct = productComboBox.getValue();
            int quantity = Integer.parseInt(quantityField.getText());

            // Find selected product and add to cart
            for (Product product : products) {
                if (product.name.equals(selectedProduct)) {
                    try {
                        Order<Product> order = new Order<>(product, quantity);
                        order.processOrder(); // Reduce stock when adding to cart
                        cart.addOrder(order);
                        System.out.println("Product added to cart.");
                    } catch (InsufficientStockException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                }
            }
            quantityField.clear();
        });

        checkoutButton.setOnAction(e -> {
            System.out.println("Checking out...");
            cart.checkout(); // Processes each order in a separate thread
        });

        // Layout setup
        VBox vbox = new VBox(10, productLabel, productComboBox, quantityLabel, quantityField, addButton, checkoutButton);
        Scene scene = new Scene(vbox, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
