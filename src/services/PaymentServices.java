package services;

import hostel.HostelManager;
import services.Fee.PaymentStatus;

import java.time.LocalDate;
import reports.ReportServices;

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

    private ReportServices reports() {
        return new ReportServices(fees, feeCount, paymentHistory, paymentCount);
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

    public void displayAllFees() {
        reports().displayAllFees();
    }

    public void displayFeesForResident(String residentId) {
        reports().displayFeesForResident(residentId);
    }

    public void displayOverdueFees() {
        reports().displayOverdueFees();
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

    public void generatePaymentReminders(int daysAhead) {
        reports().generatePaymentReminders(daysAhead);
    }

    // ─────────────────────────────────────────────
    //  FINANCIAL REPORT
    // ─────────────────────────────────────────────

    public void generateFinancialReport() {
        reports().generateFinancialReport();
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