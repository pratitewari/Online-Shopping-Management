//payment
package onlineShopping.models;

public class Payment {
    private String paymentType;
    private double amount;

    public Payment(String paymentType, double amount) {
        this.paymentType = paymentType;
        this.amount = amount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public double getAmount() {
        return amount;
    }

    public boolean processPayment() {
        // Simulate payment processing
        System.out.println("Processing " + paymentType + " payment of $" + amount);
        return true;  // Payment successful
    }
}
