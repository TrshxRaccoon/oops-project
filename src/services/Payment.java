package services;

import java.time.LocalDateTime;

public class Payment 
{
    private String residentId;
    private String feeType;
    private double amount;
    private LocalDateTime timestamp;

    public Payment(String residentId, String feeType, double amount) 
    {
        this.residentId = residentId;
        this.feeType    = feeType;
        this.amount     = amount;
        this.timestamp  = LocalDateTime.now();
    }

    public String getResidentId() { return residentId; }
    public double getAmount()     { return amount; }

    @Override
    public String toString() 
    {
        return residentId + " | " + feeType +
               " | Rs." + amount +
               " | " + timestamp;
    }
}