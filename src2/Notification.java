import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a single notification sent to a resident or broadcast to all.
 * Demonstrates: Overloaded constructors, Wrapper types
 */
public class Notification {

    public enum Priority { LOW, MEDIUM, HIGH, EMERGENCY }

    private static int notifCounter = 500;

    private String notifId;         // Unique notification ID
    private String targetUserId;    // Recipient user ID; "ALL" for broadcast
    private String title;           // Short title
    private String message;         // Full message body
    private Priority priority;      // Severity level
    private Boolean isRead;         // Whether recipient has read it
    private String timestamp;       // When it was created

    // Constructor 1: Broadcast notification (to all)
    public Notification(String title, String message) {
        this.notifId = "NOTIF-" + notifCounter++;
        this.targetUserId = "ALL";
        this.title = title;
        this.message = message;
        this.priority = Priority.MEDIUM;
        this.isRead = Boolean.FALSE;
        this.timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    // Constructor 2: Targeted notification with priority
    public Notification(String targetUserId, String title, String message, Priority priority) {
        this.notifId = "NOTIF-" + notifCounter++;
        this.targetUserId = targetUserId;
        this.title = title;
        this.message = message;
        this.priority = priority;
        this.isRead = Boolean.FALSE;
        this.timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public void markAsRead() { this.isRead = Boolean.TRUE; }

    // Getters
    public String getNotifId()      { return notifId; }
    public String getTargetUserId() { return targetUserId; }
    public String getTitle()        { return title; }
    public String getMessage()      { return message; }
    public Priority getPriority()   { return priority; }
    public Boolean isRead()         { return isRead; }
    public String getTimestamp()    { return timestamp; }

    public void display() {
        System.out.println("[" + notifId + "][" + priority + "] "
                + title + " (" + timestamp + ")"
                + (isRead ? " [READ]" : " [UNREAD]")
                + "\n   " + message);
    }
}