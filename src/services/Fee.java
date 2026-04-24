package services;
import java.time.LocalDate;

public class Fee 
{
    public static final String[] STATUS = {"PENDING", "PAID", "OVERDUE", "PARTIALLY_PAID"}; //possible fee statuses
    private String residentId;//ID of the resident associated with the fee
    private String feeType;//hostel,tuition,mess fees
    private double totalAmount;//total amount of the fee
    private double amountPaid;//amount paid
    private LocalDate dueDate; //due date for the fee
    private String status;//current status of the fee

    public Fee(String residentId, String feeType, double totalAmount, LocalDate dueDate) 
    {
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
            status = "PARTIALLY_PAID";
        }
    }

    public void checkOverdue() 
    {
        if (status != "PAID" && LocalDate.now().isAfter(dueDate))
            status = "OVERDUE";
    }

    public double getOutstanding()   
    { 
        return totalAmount - amountPaid; 
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

    public String toString() 
    {
        return residentId + " | " + feeType + " | Total: Rs." + totalAmount + " | Paid: Rs." + amountPaid + " | Due: " + dueDate + " | Status: " + status;
    }
}