package services;

import hostel.HostelManager;
import services.Fee.FeeType;
import services.Fee.PaymentStatus;
import services.Payment.PaymentMode;

import java.time.LocalDate;
import java.util.*;

public class PaymentServices {

    private final HostelManager hostelManager;
    private final Map<Integer, Fee> fees;          // feeId → Fee
    private final List<Payment> paymentHistory;

    public PaymentServices(HostelManager hostelManager) {
        this.hostelManager = hostelManager;
        this.fees = new LinkedHashMap<>();
        this.paymentHistory = new ArrayList<>();
    }

    private boolean residentExists(String residentId) {
        if (hostelManager.getResident(residentId) == null) {
            System.out.println("Resident not found: " + residentId);
            return false;
        }
        return true;
    }

    // ─────────────────────────────────────────────
    //  FEE MANAGEMENT
    // ─────────────────────────────────────────────

    public Fee addFee(String residentId, FeeType feeType, double amount, LocalDate dueDate) {
        if (!residentExists(residentId)) return null;
        Fee fee = new Fee(residentId, feeType, amount, dueDate);
        fees.put(fee.getFeeId(), fee);
        System.out.println("Fee added: " + fee);
        return fee;
    }

    public void displayAllFees() {
        System.out.println("\n============= ALL FEES =============");
        if (fees.isEmpty()) { System.out.println("No fees recorded."); return; }
        fees.values().forEach(f -> { f.checkOverdue(); System.out.println(f); });
        System.out.println("====================================");
    }

    public void displayFeesForResident(String residentId) {
        System.out.println("\n--- Fees for Resident: " + residentId + " ---");
        boolean any = false;
        for (Fee f : fees.values()) {
            if (f.getResidentId().equals(residentId)) {
                f.checkOverdue();
                System.out.println(f);
                any = true;
            }
        }
        if (!any) System.out.println("No fees found for: " + residentId);
    }

    public void displayOverdueFees() {
        System.out.println("\n========== OVERDUE FEES ==========");
        boolean any = false;
        for (Fee f : fees.values()) {
            f.checkOverdue();
            if (f.getStatus() == PaymentStatus.OVERDUE) {
                System.out.println(f);
                any = true;
            }
        }
        if (!any) System.out.println("No overdue fees.");
        System.out.println("==================================");
    }

    // ─────────────────────────────────────────────
    //  PAYMENT PROCESSING
    // ─────────────────────────────────────────────

    public boolean makePayment(int feeId, double amount, PaymentMode mode, String transactionRef) {
        Fee fee = fees.get(feeId);
        if (fee == null) {
            System.out.println("Fee ID not found: " + feeId);
            return false;
        }
        if (fee.getStatus() == PaymentStatus.PAID) {
            System.out.println("Fee #" + feeId + " is already fully paid.");
            return false;
        }
        if (amount <= 0 || amount > fee.getOutstanding()) {
            System.out.printf("Invalid amount. Max payable (outstanding): ₹%.2f%n", fee.getOutstanding());
            return false;
        }
        fee.applyPayment(amount);
        Payment payment = new Payment(feeId, fee.getResidentId(), amount, mode, transactionRef);
        paymentHistory.add(payment);
        System.out.println("Payment successful: " + payment);
        System.out.printf("Remaining for Fee #%d: ₹%.2f%n", feeId, fee.getOutstanding());
        return true;
    }

    // ─────────────────────────────────────────────
    //  REMINDERS & REPORTS
    // ─────────────────────────────────────────────

    public void generatePaymentReminders(int daysAhead) {
        LocalDate cutoff = LocalDate.now().plusDays(daysAhead);
        System.out.println("\n===== PAYMENT REMINDERS (next " + daysAhead + " days) =====");
        boolean any = false;
        for (Fee fee : fees.values()) {
            fee.checkOverdue();
            if (fee.getStatus() != PaymentStatus.PAID && !fee.getDueDate().isAfter(cutoff)) {
                System.out.printf(">> Resident: %-8s | %-20s | Outstanding: ₹%.2f | Due: %s | %s%n",
                        fee.getResidentId(), fee.getFeeType(),
                        fee.getOutstanding(), fee.getDueDate(), fee.getStatus());
                any = true;
            }
        }
        if (!any) System.out.println("No upcoming dues in this window.");
        System.out.println("=================================================");
    }

    public void generateFinancialReport() {
        System.out.println("\n============ FINANCIAL REPORT ============");
        double totalBilled    = fees.values().stream().mapToDouble(Fee::getTotalAmount).sum();
        double totalCollected = fees.values().stream().mapToDouble(Fee::getAmountPaid).sum();

        long paid    = fees.values().stream().filter(f -> f.getStatus() == PaymentStatus.PAID).count();
        long overdue = fees.values().stream().peek(Fee::checkOverdue)
                           .filter(f -> f.getStatus() == PaymentStatus.OVERDUE).count();
        long partial = fees.values().stream().filter(f -> f.getStatus() == PaymentStatus.PARTIALLY_PAID).count();
        long pending = fees.values().stream().filter(f -> f.getStatus() == PaymentStatus.PENDING).count();

        System.out.printf("Total Billed    : ₹%.2f%n", totalBilled);
        System.out.printf("Total Collected : ₹%.2f%n", totalCollected);
        System.out.printf("Outstanding     : ₹%.2f%n", totalBilled - totalCollected);
        System.out.println("------------------------------------------");
        System.out.println("Paid: " + paid + " | Overdue: " + overdue +
                           " | Partial: " + partial + " | Pending: " + pending);
        System.out.println("------------------------------------------");
        System.out.println("Breakdown by Fee Type:");
        Map<FeeType, Double> byType = new EnumMap<>(FeeType.class);
        for (Fee f : fees.values())
            byType.merge(f.getFeeType(), f.getAmountPaid(), Double::sum);
        byType.forEach((type, collected) ->
                System.out.printf("  %-25s ₹%.2f%n", type + ":", collected));

        System.out.println("\nRecent Transactions (last 5):");
        int from = Math.max(0, paymentHistory.size() - 5);
        paymentHistory.subList(from, paymentHistory.size()).forEach(System.out::println);
        System.out.println("==========================================");
    }

    public void displayPaymentHistory(String residentId) {
        System.out.println("\n--- Payment History: " + residentId + " ---");
        boolean any = false;
        for (Payment p : paymentHistory) {
            if (p.getResidentId().equals(residentId)) { System.out.println(p); any = true; }
        }
        if (!any) System.out.println("No payments recorded for: " + residentId);
    }
}