package services;

import java.time.LocalDateTime;

public class Payment {

    private static int counter = 1;

    private int paymentId;
    private int feeId;
    private String residentId;
    private double amount;
    private String mode;          // simple string instead of enum
    private String transactionRef;
    private LocalDateTime timestamp;

    public Payment(int feeId, String residentId, double amount, String mode, String transactionRef) {
        this.paymentId = counter++;
        this.feeId = feeId;
        this.residentId = residentId;
        this.amount = amount;
        this.mode = mode;
        this.transactionRef = transactionRef;
        this.timestamp = LocalDateTime.now();
    }

    public int getPaymentId() { return paymentId; }
    public int getFeeId() { return feeId; }
    public String getResidentId() { return residentId; }
    public double getAmount() { return amount; }
    public String getMode() { return mode; }
    public String getTransactionRef() { return transactionRef; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return "[Txn#" + paymentId +
                "] FeeID: " + feeId +
                " | " + residentId +
                " | ₹" + amount +
                " via " + mode +
                " | Ref: " + transactionRef +
                " | " + timestamp;
    }
}