//payment
package onlineShopping.utilities;

import onlineShopping.models.Payment;

public class PaymentProcessing {

    public static boolean processPayment(Payment payment) {
        System.out.println("Processing payment...");
        return payment.processPayment();
    }
}
