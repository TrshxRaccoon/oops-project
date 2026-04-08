import java.util.ArrayList;
import java.util.List;

/**
 * NotificationService implements the Notifiable interface and manages
 * all notification and alert operations.
 *
 * Demonstrates:
 *   - Interface implementation
 *   - Vararg method overloading
 *   - Overloaded methods
 */
public class NotificationService implements Notifiable {

    private List<Notification> notifications;   // All notifications

    public NotificationService() {
        this.notifications = new ArrayList<>();
    }

    /**
     * Sends a notification to a specific user.
     * Overloaded sendNotification — with priority
     */
    @Override
    public void sendNotification(String userId, String title, String message) {
        Notification n = new Notification(userId, title, message, Notification.Priority.MEDIUM);
        notifications.add(n);
        System.out.println("[NotifService] Notification sent to " + userId + ": " + title);
    }

    /**
     * Overloaded sendNotification — with explicit priority
     */
    public void sendNotification(String userId, String title,
                                 String message, Notification.Priority priority) {
        Notification n = new Notification(userId, title, message, priority);
        notifications.add(n);
        System.out.println("[NotifService][" + priority + "] Notification sent to "
                + userId + ": " + title);
    }

    /**
     * Broadcasts a notification to all residents.
     * Vararg overloading: accepts multiple resident IDs
     */
    @Override
    public void broadcastNotification(String title, String message, String... residentIds) {
        if (residentIds == null || residentIds.length == 0) {
            // If no specific IDs, broadcast to ALL
            Notification n = new Notification(title, message);
            notifications.add(n);
            System.out.println("[NotifService] Broadcast to ALL: " + title);
        } else {
            // Send to each specified resident
            for (String id : residentIds) {
                Notification n = new Notification(id, title, message, Notification.Priority.MEDIUM);
                notifications.add(n);
            }
            System.out.println("[NotifService] Broadcast to " + residentIds.length
                    + " residents: " + title);
        }
    }

    /**
     * Generates a security alert and sends it.
     * Overloaded sendAlert — simple alert
     */
    @Override
    public void sendAlert(String alertType, String message) {
        Alert alert = new Alert(alertType, "ALERT: " + alertType, message);
        notifications.add(alert);
        System.out.println("[NotifService] ALERT raised: " + alertType + " -> " + message);
    }

    /**
     * Overloaded sendAlert — with target user and location
     */
    public void sendAlert(String targetUserId, String alertType,
                          String message, String location) {
        Alert alert = new Alert(targetUserId, alertType, "ALERT: " + alertType, message, location);
        notifications.add(alert);
        System.out.println("[NotifService] Targeted ALERT to " + targetUserId
                + ": " + alertType + " @ " + location);
    }

    /** Marks a notification as read */
    public void markAsRead(String notifId) {
        for (Notification n : notifications) {
            if (n.getNotifId().equals(notifId)) {
                n.markAsRead();
                System.out.println("[NotifService] Notification " + notifId + " marked as read.");
                return;
            }
        }
    }

    /** Shows all notifications for a given user (or ALL broadcasts) */
    public void showNotificationsForUser(String userId) {
        System.out.println("\n===== Notifications for " + userId + " =====");
        boolean found = false;
        for (Notification n : notifications) {
            if (n.getTargetUserId().equals(userId) || n.getTargetUserId().equals("ALL")) {
                n.display();
                found = true;
            }
        }
        if (!found) System.out.println("No notifications.");
        System.out.println("==========================================\n");
    }

    /** Shows all unread notifications for a user */
    public void showUnreadNotifications(String userId) {
        System.out.println("\n===== Unread Notifications for " + userId + " =====");
        boolean found = false;
        for (Notification n : notifications) {
            if (!n.isRead()
                    && (n.getTargetUserId().equals(userId) || n.getTargetUserId().equals("ALL"))) {
                n.display();
                found = true;
            }
        }
        if (!found) System.out.println("No unread notifications.");
        System.out.println("================================================\n");
    }

    /** Lists all notifications in the system */
    public void listAllNotifications() {
        System.out.println("\n===== All Notifications =====");
        for (Notification n : notifications) n.display();
        if (notifications.isEmpty()) System.out.println("No notifications.");
        System.out.println("=============================\n");
    }

    public List<Notification> getNotifications() { return notifications; }
}