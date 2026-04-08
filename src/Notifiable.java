/**
 * Interface defining notification capabilities.
 * Demonstrates: Interface (single-level)
 * NotificationService implements this interface.
 */
public interface Notifiable {

    /**
     * Sends a notification to a specific user.
     * @param userId   Target user's ID
     * @param title    Notification title
     * @param message  Notification body
     */
    void sendNotification(String userId, String title, String message);

    /**
     * Broadcasts a message to all or multiple residents.
     * Vararg parameter allows flexible targeting.
     * @param title       Broadcast title
     * @param message     Broadcast body
     * @param residentIds Optional specific resident IDs (empty = all)
     */
    void broadcastNotification(String title, String message, String... residentIds);

    /**
     * Sends a high-priority security or system alert.
     * @param alertType  Type of alert e.g. "SECURITY", "MAINTENANCE"
     * @param message    Alert message
     */
    void sendAlert(String alertType, String message);
}