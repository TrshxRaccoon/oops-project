package services;

import hostel.HostelManager;
import services.Fee.PaymentStatus;

import java.time.LocalDate;

public class PaymentServices {

    private static final int MAX_FEES     = 200;
    private static final int MAX_PAYMENTS = 500;

    private HostelManager hostelManager;

    private Fee[] fees;
    private int feeCount;

    private Payment[] paymentHistory;
    private int paymentCount;

    public PaymentServices(HostelManager hostelManager) 
    {
        this.hostelManager  = hostelManager;
        this.fees           = new Fee[MAX_FEES];
        this.feeCount       = 0;
        this.paymentHistory = new Payment[MAX_PAYMENTS];
        this.paymentCount   = 0;
    }

    private boolean residentExists(String residentId) 
    {
        if (hostelManager.getResident(residentId) == null) 
        {
            System.out.println("Resident not found: " + residentId);
            return false;
        }
        return true;
    }

    // ─────────────────────────────────────────────
    //  FEE MANAGEMENT
    // ─────────────────────────────────────────────

    public void addFee(String residentId, String feeType, double amount, LocalDate dueDate) 
    {
        if (!residentExists(residentId)) return;
        if (feeCount >= MAX_FEES) 
        {
            System.out.println("Fee list is full.");
            return;
        }
        fees[feeCount] = new Fee(residentId, feeType, amount, dueDate);
        feeCount++;
        System.out.println("Fee added.");
    }

    public void displayAllFees() 
    {
        System.out.println("\n===== ALL FEES =====");
        if (feeCount == 0) 
        {
            System.out.println("No fees recorded.");
            return;
        }
        for (int i = 0; i < feeCount; i++) 
        {
            fees[i].checkOverdue();
            System.out.println(fees[i]);
        }
        System.out.println("====================");
    }

    public void displayFeesForResident(String residentId) 
    {
        System.out.println("\n--- Fees for: " + residentId + " ---");
        boolean any = false;
        for (int i = 0; i < feeCount; i++) 
        {
            if (fees[i].getResidentId().equals(residentId)) 
            {
                fees[i].checkOverdue();
                System.out.println(fees[i]);
                any = true;
            }
        }
        if (!any) System.out.println("No fees found for: " + residentId);
    }

    public void displayOverdueFees() 
    {
        System.out.println("\n===== OVERDUE FEES =====");
        boolean any = false;
        for (int i = 0; i < feeCount; i++) 
        {
            fees[i].checkOverdue();
            if (fees[i].getStatus() == PaymentStatus.OVERDUE) 
            {
                System.out.println(fees[i]);
                any = true;
            }
        }
        if (!any) System.out.println("No overdue fees.");
    }

    // ─────────────────────────────────────────────
    //  PAYMENT PROCESSING
    // ─────────────────────────────────────────────

    public boolean makePayment(String residentId, double amount) 
    {
        Fee fee = null;
        for (int i = 0; i < feeCount; i++) 
        {
            if (fees[i].getResidentId().equals(residentId) && fees[i].getStatus() != PaymentStatus.PAID) 
            {
                fee = fees[i];
                break;
            }
        }

        if (fee == null) 
        {
            System.out.println("No unpaid fees found for: " + residentId);
            return false;
        }
        if (amount <= 0 || amount > fee.getOutstanding()) 
        {
            System.out.println("Invalid amount. Max payable: Rs." + fee.getOutstanding());
            return false;
        }
        if (paymentCount >= MAX_PAYMENTS) 
        {
            System.out.println("Payment history is full.");
            return false;
        }

        fee.applyPayment(amount);
        paymentHistory[paymentCount] = new Payment(residentId, fee.getFeeType(), amount);
        paymentCount++;

        System.out.println("Payment successful.");
        System.out.println("Remaining: Rs." + fee.getOutstanding());
        return true;
    }

    // ─────────────────────────────────────────────
    //  REMINDERS
    // ─────────────────────────────────────────────

    public void generatePaymentReminders(int daysAhead) 
    {
        LocalDate cutoff = LocalDate.now().plusDays(daysAhead);
        System.out.println("\n===== PAYMENT REMINDERS (next " + daysAhead + " days) =====");
        boolean any = false;
        for (int i = 0; i < feeCount; i++) 
        {
            fees[i].checkOverdue();
            if (fees[i].getStatus() != PaymentStatus.PAID && !fees[i].getDueDate().isAfter(cutoff)) 
            {
                System.out.println(">> " + fees[i]);
                any = true;
            }
        }
        if (!any) System.out.println("No upcoming dues.");
        System.out.println("=================================================");
    }

    // ─────────────────────────────────────────────
    //  FINANCIAL REPORT
    // ─────────────────────────────────────────────

    public void generateFinancialReport() 
    {
        System.out.println("\n===== FINANCIAL REPORT =====");

        double totalBilled    = 0;
        double totalCollected = 0;
        int paid = 0, overdue = 0, partial = 0, pending = 0;

        for (int i = 0; i < feeCount; i++) 
        {
            fees[i].checkOverdue();
            totalBilled    += fees[i].getTotalAmount();
            totalCollected += fees[i].getAmountPaid();
            switch (fees[i].getStatus()) 
            {
                case PAID:           paid++;    break;
                case OVERDUE:        overdue++; break;
                case PARTIALLY_PAID: partial++; break;
                case PENDING:        pending++; break;
            }
        }

        System.out.println("Total Billed    : Rs." + totalBilled);
        System.out.println("Total Collected : Rs." + totalCollected);
        System.out.println("Outstanding     : Rs." + (totalBilled - totalCollected));
        System.out.println("----------------------------");
        System.out.println("Paid: " + paid + " | Overdue: " + overdue +
                           " | Partial: " + partial + " | Pending: " + pending);
        System.out.println("----------------------------");
        System.out.println("Recent Transactions (last 5):");
        int start = Math.max(0, paymentCount - 5);
        for (int i = start; i < paymentCount; i++) 
        {
            System.out.println(paymentHistory[i]);
        }
        System.out.println("============================");
    }

    public void displayPaymentHistory(String residentId) 
    {
        System.out.println("\n--- Payment History: " + residentId + " ---");
        boolean any = false;
        for (int i = 0; i < paymentCount; i++) 
        {
            if (paymentHistory[i].getResidentId().equals(residentId)) 
            {
                System.out.println(paymentHistory[i]);
                any = true;
            }
        }
        if (!any) System.out.println("No payments recorded for: " + residentId);
    }
}