import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Payment {
    private static int payCounter = 600;

    private String paymentId;
    private String residentId;
    private String feeId;
    private Double amountPaid;
    private String paymentMode;   // CASH, UPI, CARD
    private String timestamp;

    // Constructor 1
    public Payment(String residentId, String feeId, double amountPaid) {
        this.paymentId = "PAY-" + payCounter++;
        this.residentId = residentId;
        this.feeId = feeId;
        this.amountPaid = Double.valueOf(amountPaid);
        this.paymentMode = "CASH";
        this.timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    // Constructor 2
    public Payment(String residentId, String feeId, double amountPaid, String paymentMode) {
        this(residentId, feeId, amountPaid);
        this.paymentMode = paymentMode;
    }

    public String getPaymentId()  { return paymentId; }
    public String getResidentId() { return residentId; }
    public String getFeeId()      { return feeId; }
    public Double getAmountPaid() { return amountPaid; }

    public void display() {
        System.out.println("[" + paymentId + "] Resident: " + residentId
                + " | Fee: " + feeId
                + " | Paid: ₹" + amountPaid
                + " | Mode: " + paymentMode
                + " | Time: " + timestamp);
    }
}