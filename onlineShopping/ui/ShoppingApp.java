//shoppingapp
package onlineShopping.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import onlineShopping.models.*;
import onlineShopping.utilities.ShoppingCart;
import onlineShopping.utilities.PaymentProcessing;

public class ShoppingApp extends Application {

    private User currentUser;
    private ShoppingCart<Product> cart;

    @Override
    public void start(Stage primaryStage) {
        currentUser = new User("user1", "password123");
        cart = new ShoppingCart<>(currentUser);

        primaryStage.setTitle("Online Shopping");

        Label label = new Label("Products:");
        ListView<Product> productList = new ListView<>();
        productList.getItems().addAll(
                new ElectronicProduct("Laptop", 999.99, 2),
                new ClothingProduct("T-Shirt", 19.99, "L"),
                new ElectronicProduct("Smartphone", 699.99, 1),
                new ClothingProduct("Jeans", 39.99, "M")
        );

        Button addButton = new Button("Add to Cart");
        addButton.setOnAction(e -> {
            Product selectedProduct = productList.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                cart.addProduct(selectedProduct, 1);
            }
        });

        Button checkoutButton = new Button("Checkout");
        checkoutButton.setOnAction(e -> checkout());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, productList, addButton, checkoutButton);

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        loginDialog();  // Show login dialog on app startup
    }

    private void loginDialog() {
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Login");

        // Dialog elements
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        ButtonType loginButton = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButton, ButtonType.CANCEL);

        VBox content = new VBox(10);
        content.getChildren().addAll(new Label("Username:"), usernameField, new Label("Password:"), passwordField);
        dialog.getDialogPane().setContent(content);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButton) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                return currentUser.authenticate(username, password);
            }
            return false;
        });

        dialog.showAndWait().ifPresent(isAuthenticated -> {
            if (!isAuthenticated) {
                System.out.println("Login failed.");
                loginDialog();  // Re-open login dialog if authentication failed
            } else {
                System.out.println("Login successful.");
            }
        });
    }

    private void checkout() {
        double total = cart.getTotalPrice();
        if (total > 0) {
            Payment payment = new Payment("Credit Card", total);
            if (PaymentProcessing.processPayment(payment)) {
                System.out.println("Payment successful! Total: $" + total);
            } else {
                System.out.println("Payment failed.");
            }
        } else {
            System.out.println("Your cart is empty.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
