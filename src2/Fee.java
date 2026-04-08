import java.time.LocalDate;

public class Fee {
    public enum Status { PAID, UNPAID, OVERDUE }

    private static int feeCounter = 400;
    private String feeId;
    private String residentId;
    private Double amount;          // Double wrapper
    private String description;
    private Status status;
    private String dueDate;
    private String paidDate;

    // Constructor 1: basic
    public Fee(String residentId, double amount, String description, String dueDate) {
        this.feeId = "FEE-" + feeCounter++;
        this.residentId = residentId;
        this.amount = Double.valueOf(amount);   // wrapper
        this.description = description;
        this.dueDate = dueDate;
        this.status = Status.UNPAID;
        this.paidDate = "Not Paid";
    }

    // Constructor 2: with status
    public Fee(String residentId, double amount, String description, String dueDate, Status status) {
        this(residentId, amount, description, dueDate);
        this.status = status;
    }

    public void markPaid() {
        this.status = Status.PAID;
        this.paidDate = LocalDate.now().toString();
    }

    public void markOverdue() { this.status = Status.OVERDUE; }

    public String getFeeId()      { return feeId; }
    public String getResidentId() { return residentId; }
    public Double getAmount()     { return amount; }
    public Status getStatus()     { return status; }
    public String getDueDate()    { return dueDate; }

    public void display() {
        System.out.println("[" + feeId + "] Resident: " + residentId
                + " | " + description
                + " | Amount: ₹" + amount
                + " | Due: " + dueDate
                + " | Status: " + status
                + " | Paid: " + paidDate);
    }
}