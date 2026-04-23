package reports;
import java.time.LocalDate;
import services.Fee;
import services.Payment;

public class ReportServices 
{
    private Fee[] fees;
    private int feeCount;
    private Payment[] paymentHistory;
    private int paymentCount;

    public ReportServices(Fee[] fees, int feeCount, Payment[] paymentHistory, int paymentCount)
    {
        this.fees = fees;
        this.feeCount = feeCount;
        this.paymentHistory = paymentHistory;
        this.paymentCount = paymentCount;
    }

    public void generateFinancialReport() 
    {
        System.out.println("\nFINANCIAL REPORT:");

        double totalBilled = 0;
        double totalCollected = 0;
        int paid = 0, overdue = 0, partial = 0, pending = 0;

        for (int i = 0; i < feeCount; i++) 
        {
            fees[i].checkOverdue();
            totalBilled += fees[i].getTotalAmount();
            totalCollected += fees[i].getAmountPaid();

            switch (fees[i].getStatus()) 
            {
                case "PAID": paid++; break;
                case "OVERDUE": overdue++; break;
                case "PARTIALLY_PAID": partial++; break;
                case "PENDING": pending++; break;
            }
        }

        System.out.println("Total Billed: Rs." + totalBilled);
        System.out.println("Total Collected: Rs." + totalCollected);
        System.out.println("Outstanding: Rs." + (totalBilled - totalCollected));
        System.out.println("\nPaid: " + paid + " | Overdue: " + overdue + " | Partial: " + partial + " | Pending: " + pending);
        System.out.println("\nRecent Transactions (last 5):");

        int start = Math.max(0, paymentCount - 5);
        for (int i = start; i < paymentCount; i++) 
        {
            System.out.println(paymentHistory[i]);
        }
    }

    public void generatePaymentReminders(int daysAhead)
    {
        LocalDate cutoff = LocalDate.now().plusDays(daysAhead);
        System.out.println("\nPAYMENT REMINDERS (next " + daysAhead + " days)");
        boolean any = false;

        for (int i = 0; i < feeCount; i++) 
        {
            fees[i].checkOverdue();

            if (!"PAID".equals(fees[i].getStatus()) &&
                !fees[i].getDueDate().isAfter(cutoff)) 
            {
                System.out.println(">> " + fees[i]);
                any = true;
            }
        }

        if (!any) 
        {
            System.out.println("No upcoming dues");
        }
    }

    public void displayOverdueFees() {
        System.out.println("\nOVERDUE FEES:");
        boolean any = false;

        for (int i = 0; i < feeCount; i++) 
        {
            fees[i].checkOverdue();
            if ("OVERDUE".equals(fees[i].getStatus())) 
            {
                System.out.println(fees[i]);
                any = true;
            }
        }

        if (!any) 
        {
            System.out.println("No overdue fees.");
        }
    }

    public void displayFeesForResident(String residentId) 
    {
        System.out.println("\nFees for: " + residentId);
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

        if (!any) 
        {
            System.out.println("No fees found for:" + residentId);
        }
    }

    public void displayAllFees() {
        System.out.println("\nALL FEES:");

        if (feeCount == 0) 
        {
            System.out.println("No fees recorded");
            return;
        }

        for (int i = 0; i < feeCount; i++) 
        {
            fees[i].checkOverdue();
            System.out.println(fees[i]);
        }
    }
}