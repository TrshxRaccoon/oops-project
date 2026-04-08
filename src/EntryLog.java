import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a single entry or exit record for a resident.
 * Demonstrates: Wrapper types (Boolean, Integer)
 */
public class EntryLog {

    private static int logCounter = 1000; // Auto-increment log ID using Integer wrapper

    private String logId;           // Unique log ID
    private String residentId;      // ID of the resident
    private String residentName;    // Name of the resident
    private String entryTime;       // Timestamp of entry
    private String exitTime;        // Timestamp of exit (null if still inside)
    private Boolean isEntry;        // true = entry, false = exit
    private String purpose;         // Purpose of the visit/exit

    // Constructor 1: Entry log
    public EntryLog(String residentId, String residentName, boolean isEntry) {
        this.logId = "LOG-" + Integer.toString(logCounter++); // Using Integer wrapper
        this.residentId = residentId;
        this.residentName = residentName;
        this.isEntry = Boolean.valueOf(isEntry);               // Boolean wrapper
        this.purpose = "General";
        String now = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if (isEntry) {
            this.entryTime = now;
            this.exitTime = "Still Inside";
        } else {
            this.entryTime = "N/A";
            this.exitTime = now;
        }
    }

    // Constructor 2: With purpose
    public EntryLog(String residentId, String residentName, boolean isEntry, String purpose) {
        this(residentId, residentName, isEntry);
        this.purpose = purpose;
    }

    // Setters
    public void recordExit() {
        this.isEntry = Boolean.FALSE;
        this.exitTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // Getters
    public String getLogId()        { return logId; }
    public String getResidentId()   { return residentId; }
    public String getResidentName() { return residentName; }
    public String getEntryTime()    { return entryTime; }
    public String getExitTime()     { return exitTime; }
    public Boolean isEntry()        { return isEntry; }
    public String getPurpose()      { return purpose; }

    public void displayLog() {
        System.out.println("[" + logId + "] " + residentName
                + " | " + (isEntry ? "ENTRY" : "EXIT")
                + " | In: " + entryTime
                + " | Out: " + exitTime
                + " | Purpose: " + purpose);
    }
}