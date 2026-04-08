import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Visitor {
    private static int visitorCounter = 700;
    private String visitorId;
    private String visitorName;
    private String contactNumber;
    private String purposeOfVisit;
    private String residentToVisit;   // Resident student ID
    private String entryTime;
    private String exitTime;
    private boolean isInside;

    public Visitor(String visitorName, String contactNumber,
                   String purposeOfVisit, String residentToVisit) {
        this.visitorId = "VIS-" + visitorCounter++;
        this.visitorName = visitorName;
        this.contactNumber = contactNumber;
        this.purposeOfVisit = purposeOfVisit;
        this.residentToVisit = residentToVisit;
        this.entryTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.exitTime = "Still Inside";
        this.isInside = true;
    }

    public void recordExit() {
        this.exitTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.isInside = false;
    }

    public String getVisitorId()       { return visitorId; }
    public String getVisitorName()     { return visitorName; }
    public String getContactNumber()   { return contactNumber; }
    public String getPurposeOfVisit()  { return purposeOfVisit; }
    public String getResidentToVisit() { return residentToVisit; }
    public boolean isInside()          { return isInside; }

    public void display() {
        System.out.println("[" + visitorId + "] " + visitorName
                + " | Contact: " + contactNumber
                + " | Visiting: " + residentToVisit
                + " | Purpose: " + purposeOfVisit
                + " | In: " + entryTime
                + " | Out: " + exitTime);
    }
}