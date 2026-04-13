package services;

import java.time.LocalDate;

public class Fee {
    public enum FeeType { HOSTEL_RENT, MESS, MAINTENANCE, SECURITY_DEPOSIT, MISCELLANEOUS }
    public enum PaymentStatus { PENDING, PAID, OVERDUE, PARTIALLY_PAID }

    private static int counter = 1;

    private int feeId;
    private String residentId;
    private FeeType feeType;
    private double totalAmount;
    private double amountPaid;
    private LocalDate dueDate;
    private PaymentStatus status;

    public Fee(String residentId, FeeType feeType, double totalAmount, LocalDate dueDate) {
        this.feeId = counter++;
        this.residentId = residentId;
        this.feeType = feeType;
        this.totalAmount = totalAmount;
        this.amountPaid = 0.0;
        this.dueDate = dueDate;
        this.status = PaymentStatus.PENDING;
    }

    public void applyPayment(double amount) {
        amountPaid += amount;
        if (amountPaid >= totalAmount) {
            amountPaid = totalAmount;
            status = PaymentStatus.PAID;
        } else {
            status = PaymentStatus.PARTIALLY_PAID;
        }
    }

    public void checkOverdue() {
        if (status != PaymentStatus.PAID && LocalDate.now().isAfter(dueDate)) {
            status = PaymentStatus.OVERDUE;
        }
    }

    public double getOutstanding() { return totalAmount - amountPaid; }

    public int getFeeId() { return feeId; }
    public String getResidentId() { return residentId; }
    public FeeType getFeeType() { return feeType; }
    public double getTotalAmount() { return totalAmount; }
    public double getAmountPaid() { return amountPaid; }
    public LocalDate getDueDate() { return dueDate; }
    public PaymentStatus getStatus() { return status; }

    @Override
    public String toString() {
        return String.format("[Fee#%d] %s | %s | Total: ₹%.2f | Paid: ₹%.2f | Due: %s | Status: %s",
                feeId, residentId, feeType, totalAmount, amountPaid, dueDate, status);
    }
}