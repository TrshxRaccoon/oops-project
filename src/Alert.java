/**
 * An Alert is a high-priority special notification (e.g., security breach, rule violation).
 * Extends Notification — demonstrates Hierarchical Inheritance from Notification.
 */
public class Alert extends Notification {

    private String alertType;   // e.g., "SECURITY", "RULE_VIOLATION", "MAINTENANCE"
    private String location;    // Where the alert was triggered

    // Constructor 1: Alert to all with type
    public Alert(String alertType, String title, String message) {
        super(title, message); // calls Notification(title, message) — broadcast
        this.alertType = alertType;
        this.location = "General";
        // Override priority to HIGH for alerts
    }

    // Constructor 2: Targeted alert with location
    public Alert(String targetUserId, String alertType, String title,
                 String message, String location) {
        super(targetUserId, title, message, Priority.HIGH);
        this.alertType = alertType;
        this.location = location;
    }

    // Getters
    public String getAlertType() { return alertType; }
    public String getLocation()  { return location; }

    @Override
    public void display() {
        System.out.println("🚨 ALERT [" + getNotifId() + "][" + alertType + "] "
                + getTitle() + " @ " + location
                + " (" + getTimestamp() + ")"
                + "\n   " + getMessage());
    }
}