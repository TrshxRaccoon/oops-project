package services;
import java.time.LocalDateTime;

public class Payment 
{
    private String residentId;//ID of the resident making the payment
    private String feeType;//hostel,tuition,mess fees
    private double amount;//amount paid
    private LocalDateTime timestamp;//timestamp of the payment

    public Payment(String residentId, String feeType, double amount) 
    {
        this.residentId = residentId;
        this.feeType = feeType;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    public String getResidentId()
    {
        return residentId;
    }

    public double getAmount()
    {
        return amount;
    }

    public String toString() 
    {
        return residentId + " | " + feeType + " | Rs." + amount + " | " + timestamp;
    }
}