package services;

import hostel.HostelManager;
import java.time.LocalDate;

public class PaymentServices {

    private HostelManager hostelManager;

    private Fee[] fees;
    private int feeCount;

    private Payment[] paymentHistory;
    private int paymentCount;

    private static final int MAX_FEES = 200;
    private static final int MAX_PAYMENTS = 500;

    public PaymentServices(HostelManager hostelManager) {
        this.hostelManager = hostelManager;
        this.fees = new Fee[MAX_FEES];
        this.paymentHistory = new Payment[MAX_PAYMENTS];
        this.feeCount = 0;
        this.paymentCount = 0;
    }

    private boolean residentExists(String residentId) {
        if (hostelManager.getResident(residentId) == null) {
            System.out.println("Resident not found: " + residentId);
            return false;
        }
        return true;
    }

    // ───── ADD FEE ─────

    public void addFee(String residentId, String feeType, double amount, LocalDate dueDate) {

        if (!residentExists(residentId)) return;

        if (feeCount >= MAX_FEES) {
            System.out.println("Fee storage full.");
            return;
        }

        fees[feeCount] = new Fee(residentId, feeType, amount, dueDate);
        System.out.println("Fee added: " + fees[feeCount]);

        feeCount++;
    }

    // ───── DISPLAY ALL ─────

    public void displayAllFees() {
        System.out.println("\n--- ALL FEES ---");

        if (feeCount == 0) {
            System.out.println("No fees.");
            return;
        }

        for (int i = 0; i < feeCount; i++) {
            fees[i].checkOverdue();
            System.out.println(fees[i]);
        }
    }

    // ───── DISPLAY BY RESIDENT ─────

    public void displayFeesForResident(String residentId) {

        System.out.println("\n--- Fees for " + residentId + " ---");

        boolean found = false;

        for (int i = 0; i < feeCount; i++) {
            if (fees[i].getResidentId().equals(residentId)) {
                fees[i].checkOverdue();
                System.out.println(fees[i]);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No fees found.");
        }
    }

    // ───── FIND FEE BY ID ─────

    private Fee findFee(int feeId) {
        for (int i = 0; i < feeCount; i++) {
            if (fees[i].getFeeId() == feeId) {
                return fees[i];
            }
        }
        return null;
    }

    // ───── MAKE PAYMENT ─────

    public void makePayment(int feeId, double amount, String mode) {

        Fee fee = findFee(feeId);

        if (fee == null) {
            System.out.println("Invalid Fee ID.");
            return;
        }

        if (fee.getStatus().equals("PAID")) {
            System.out.println("Already paid.");
            return;
        }

        if (amount <= 0 || amount > fee.getOutstanding()) {
            System.out.println("Invalid amount.");
            return;
        }

        fee.applyPayment(amount);

        if (paymentCount >= MAX_PAYMENTS) {
            System.out.println("Payment storage full.");
            return;
        }

        paymentHistory[paymentCount] =
                new Payment(feeId, fee.getResidentId(), amount, mode);

        paymentCount++;

        System.out.println("Payment successful.");
        System.out.println("Remaining: ₹" + fee.getOutstanding());
    }

    // ───── REMINDERS ─────

    public void generatePaymentReminders(int daysAhead) {

        LocalDate cutoff = LocalDate.now().plusDays(daysAhead);

        System.out.println("\n--- PAYMENT REMINDERS ---");

        boolean found = false;

        for (int i = 0; i < feeCount; i++) {

            fees[i].checkOverdue();

            if (!fees[i].getStatus().equals("PAID") &&
                !fees[i].getDueDate().isAfter(cutoff)) {

                System.out.println("Resident: " + fees[i].getResidentId() +
                        " | Amount: ₹" + fees[i].getOutstanding() +
                        " | Due: " + fees[i].getDueDate());

                found = true;
            }
        }

        if (!found) {
            System.out.println("No upcoming dues.");
        }
    }

    // ───── REPORT ─────

    public void generateFinancialReport() {

        double totalBilled = 0;
        double totalCollected = 0;

        int paid = 0, overdue = 0, partial = 0, pending = 0;

        for (int i = 0; i < feeCount; i++) {

            totalBilled += fees[i].getTotalAmount();
            totalCollected += fees[i].getAmountPaid();

            String status = fees[i].getStatus();

            if (status.equals("PAID")) paid++;
            else if (status.equals("OVERDUE")) overdue++;
            else if (status.equals("PARTIAL")) partial++;
            else pending++;
        }

        System.out.println("\n--- FINANCIAL REPORT ---");
        System.out.println("Total Billed: ₹" + totalBilled);
        System.out.println("Total Collected: ₹" + totalCollected);
        System.out.println("Outstanding: ₹" + (totalBilled - totalCollected));

        System.out.println("Paid: " + paid +
                " | Overdue: " + overdue +
                " | Partial: " + partial +
                " | Pending: " + pending);

        System.out.println("\nRecent Payments:");

        int start = paymentCount - 5;
        if (start < 0) start = 0;

        for (int i = start; i < paymentCount; i++) {
            System.out.println(paymentHistory[i]);
        }
    }

    // ───── HISTORY ─────

    public void displayPaymentHistory(String residentId) {

        System.out.println("\n--- Payment History ---");

        boolean found = false;

        for (int i = 0; i < paymentCount; i++) {
            if (paymentHistory[i].getResidentId().equals(residentId)) {
                System.out.println(paymentHistory[i]);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No records.");
        }
    }
}