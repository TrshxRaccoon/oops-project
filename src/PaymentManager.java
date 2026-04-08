import java.util.ArrayList;
import java.util.List;

public class PaymentManager {

    private List<Fee> fees;
    private List<Payment> payments;
    private NotificationService notifService;

    public PaymentManager(NotificationService notifService) {
        this.fees = new ArrayList<>();
        this.payments = new ArrayList<>();
        this.notifService = notifService;
    }

    public void addFee(Fee fee) {
        fees.add(fee);
        System.out.println("[PaymentManager] Fee added: " + fee.getFeeId());
    }

    // Overloaded processPayment — by feeId
    public void processPayment(String residentId, String feeId, double amount)
            throws PaymentException {
        Fee fee = findFee(feeId);
        if (fee.getStatus() == Fee.Status.PAID) {
            throw new PaymentException("Fee " + feeId + " is already paid.");
        }
        if (amount < fee.getAmount()) {
            throw new PaymentException("Insufficient amount. Required: ₹" + fee.getAmount());
        }
        fee.markPaid();
        Payment p = new Payment(residentId, feeId, amount);
        payments.add(p);
        notifService.sendNotification(residentId, "Payment Confirmed",
                "Your payment of ₹" + amount + " for " + feeId + " is confirmed.");
        System.out.println("[PaymentManager] Payment processed: " + p.getPaymentId());
    }

    // Overloaded processPayment — with mode
    public void processPayment(String residentId, String feeId, double amount, String mode)
            throws PaymentException {
        Fee fee = findFee(feeId);
        if (fee.getStatus() == Fee.Status.PAID) {
            throw new PaymentException("Fee " + feeId + " already paid.");
        }
        fee.markPaid();
        Payment p = new Payment(residentId, feeId, amount, mode);
        payments.add(p);
        System.out.println("[PaymentManager] Payment via " + mode + ": " + p.getPaymentId());
    }

    private Fee findFee(String feeId) throws PaymentException {
        for (Fee f : fees) {
            if (f.getFeeId().equals(feeId)) return f;
        }
        throw new PaymentException("Fee not found: " + feeId);
    }

    // Send payment reminders for unpaid fees
    public void sendPaymentReminders() {
        for (Fee f : fees) {
            if (f.getStatus() == Fee.Status.UNPAID || f.getStatus() == Fee.Status.OVERDUE) {
                notifService.sendNotification(f.getResidentId(),
                        "Payment Reminder",
                        "Your fee " + f.getFeeId() + " of ₹"
                                + f.getAmount() + " is due by " + f.getDueDate());
            }
        }
        System.out.println("[PaymentManager] Payment reminders sent.");
    }

    public void listAllFees() {
        System.out.println("\n===== All Fees =====");
        if (fees.isEmpty()) System.out.println("No fees registered.");
        else for (Fee f : fees) f.display();
        System.out.println("====================\n");
    }

    public void listPayments() {
        System.out.println("\n===== Payment History =====");
        if (payments.isEmpty()) System.out.println("No payments made.");
        else for (Payment p : payments) p.display();
        System.out.println("===========================\n");
    }

    public List<Fee> getFees()         { return fees; }
    public List<Payment> getPayments() { return payments; }
}