package services;
import java.time.LocalDate;

public class Fee 
{

    private static int counter = 1;

    private int feeId;
    private String residentId;
    private String feeType;
    private double totalAmount;
    private double amountPaid;
    private LocalDate dueDate;
    private String status;

    public Fee(String residentId, String feeType, double totalAmount, LocalDate dueDate) 
    {
        this.feeId = counter++;
        this.residentId = residentId;
        this.feeType = feeType;
        this.totalAmount = totalAmount;
        this.amountPaid = 0.0;
        this.dueDate = dueDate;
        this.status = "PENDING";
    }

    public void applyPayment(double amount) 
    {
        amountPaid += amount;

        if (amountPaid >= totalAmount) 
        {
            amountPaid = totalAmount;
            status = "PAID";
        } 
        else 
        {
            status = "PARTIAL";
        }
    }

    public void checkOverdue() 
    {
        if (!status.equals("PAID") && LocalDate.now().isAfter(dueDate)) 
        {
            status = "OVERDUE";
        }
    }

    public double getOutstanding() 
    {
        return totalAmount - amountPaid;
    }

    public int getFeeId() 
    { 
        return feeId; 
    }

    public String getResidentId() 
    { 
        return residentId; 
    }

    public String getFeeType() 
    { 
        return feeType; 
    }

    public double getTotalAmount() 
    { 
        return totalAmount; 
    }

    public double getAmountPaid() 
    { 
        return amountPaid; 
    }

    public LocalDate getDueDate() 
    { 
        return dueDate; 
    }

    public String getStatus() 
    { 
        return status; 
    }

    @Override
    public String toString() 
    {
        return "[Fee#" + feeId + "] " + residentId + " | " + feeType +
                " | Total: ₹" + totalAmount +
                " | Paid: ₹" + amountPaid +
                " | Due: " + dueDate +
                " | Status: " + status;
    }
}