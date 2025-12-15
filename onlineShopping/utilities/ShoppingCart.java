//shoppingcart
package onlineShopping.utilities;

import onlineShopping.models.Product;
import onlineShopping.models.User;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart<T extends Product> {
    private User user;
    private List<CartItem> items = new ArrayList<>();

    public ShoppingCart(User user) {
        this.user = user;
    }

    // Nested class to track items in the cart
    private class CartItem {
        T product;
        int quantity;

        public CartItem(T product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public double getTotalPrice() {
            return product.getPrice() * quantity;
        }
    }

    public void addProduct(T product, int quantity) {
        if (user.isAuthenticated()) {
            items.add(new CartItem(product, quantity));
        } else {
            System.out.println("Please log in to add products to the cart.");
        }
    }

    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }
}
