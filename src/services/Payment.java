package services;

import java.time.LocalDateTime;

public class Payment {
    public enum PaymentMode { CASH, UPI, BANK_TRANSFER, CARD }

    private static int counter = 1;

    private int paymentId;
    private int feeId;
    private String residentId;
    private double amount;
    private PaymentMode mode;
    private String transactionRef;   // UPI txn ID, or cash receipt number
    private LocalDateTime timestamp;

    public Payment(int feeId, String residentId, double amount, PaymentMode mode, String transactionRef) {
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
    public PaymentMode getMode() { return mode; }
    public String getTransactionRef() { return transactionRef; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return String.format("[Txn#%d] FeeID: %d | %s | ₹%.2f via %s | Ref: %s | %s",
                paymentId, feeId, residentId, amount, mode, transactionRef, timestamp);
    }
}