package reports;

import java.time.LocalDate;

import services.Fee;
import services.Fee.PaymentStatus;
import services.Payment;

public class ReportServices {

    private Fee[] fees;
    private int feeCount;

    private Payment[] paymentHistory;
    private int paymentCount;

    public ReportServices(Fee[] fees, int feeCount, Payment[] paymentHistory, int paymentCount) {
        this.fees = fees;
        this.feeCount = feeCount;
        this.paymentHistory = paymentHistory;
        this.paymentCount = paymentCount;
    }

    // ─────────────────────────────────────────────
    //  FINANCIAL REPORT
    // ─────────────────────────────────────────────

    public void generateFinancialReport() {
        System.out.println("\n===== FINANCIAL REPORT =====");

        double totalBilled = 0;
        double totalCollected = 0;

        int paid = 0, overdue = 0, partial = 0, pending = 0;

        for (int i = 0; i < feeCount; i++) {
            fees[i].checkOverdue();

            totalBilled += fees[i].getTotalAmount();
            totalCollected += fees[i].getAmountPaid();

            switch (fees[i].getStatus()) {
                case PAID: paid++; break;
                case OVERDUE: overdue++; break;
                case PARTIALLY_PAID: partial++; break;
                case PENDING: pending++; break;
            }
        }

        System.out.println("Total Billed    : Rs." + totalBilled);
        System.out.println("Total Collected : Rs." + totalCollected);
        System.out.println("Outstanding     : Rs." + (totalBilled - totalCollected));

        System.out.println("----------------------------");
        System.out.println("Paid: " + paid +
                           " | Overdue: " + overdue +
                           " | Partial: " + partial +
                           " | Pending: " + pending);
        System.out.println("----------------------------");

        System.out.println("Recent Transactions (last 5):");

        int start = Math.max(0, paymentCount - 5);

        for (int i = start; i < paymentCount; i++) {
            System.out.println(paymentHistory[i]);
        }

        System.out.println("============================");
    }

    // ─────────────────────────────────────────────
    //  PAYMENT REMINDERS
    // ─────────────────────────────────────────────

    public void generatePaymentReminders(int daysAhead) {
        LocalDate cutoff = LocalDate.now().plusDays(daysAhead);

        System.out.println("\n===== PAYMENT REMINDERS (next " + daysAhead + " days) =====");

        boolean any = false;

        for (int i = 0; i < feeCount; i++) {
            fees[i].checkOverdue();

            if (fees[i].getStatus() != PaymentStatus.PAID &&
                !fees[i].getDueDate().isAfter(cutoff)) {

                System.out.println(">> " + fees[i]);
                any = true;
            }
        }

        if (!any) {
            System.out.println("No upcoming dues.");
        }

        System.out.println("=================================================");
    }

    // ─────────────────────────────────────────────
    //  OVERDUE FEES
    // ─────────────────────────────────────────────

    public void displayOverdueFees() {
        System.out.println("\n===== OVERDUE FEES =====");

        boolean any = false;

        for (int i = 0; i < feeCount; i++) {
            fees[i].checkOverdue();

            if (fees[i].getStatus() == PaymentStatus.OVERDUE) {
                System.out.println(fees[i]);
                any = true;
            }
        }

        if (!any) {
            System.out.println("No overdue fees.");
        }
    }

    // ─────────────────────────────────────────────
    //  FEES PER RESIDENT
    // ─────────────────────────────────────────────

    public void displayFeesForResident(String residentId) {
        System.out.println("\n--- Fees for: " + residentId + " ---");

        boolean any = false;

        for (int i = 0; i < feeCount; i++) {
            if (fees[i].getResidentId().equals(residentId)) {
                fees[i].checkOverdue();
                System.out.println(fees[i]);
                any = true;
            }
        }

        if (!any) {
            System.out.println("No fees found for: " + residentId);
        }
    }

    // ─────────────────────────────────────────────
    //  ALL FEES
    // ─────────────────────────────────────────────

    public void displayAllFees() {
        System.out.println("\n===== ALL FEES =====");

        if (feeCount == 0) {
            System.out.println("No fees recorded.");
            return;
        }

        for (int i = 0; i < feeCount; i++) {
            fees[i].checkOverdue();
            System.out.println(fees[i]);
        }

        System.out.println("====================");
    }
}