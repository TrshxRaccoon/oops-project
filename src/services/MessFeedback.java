package services;

import java.time.LocalDateTime;

public class MessFeedback {
    private static int counter = 1;

    private int feedbackId;
    private String residentId;
    private String mealDescription;   // e.g. "Monday Dinner"
    private int rating;               // 1-5
    private String comment;
    private LocalDateTime timestamp;
    private boolean resolved;

    public MessFeedback(String residentId, String mealDescription, int rating, String comment) {
        if (rating < 1 || rating > 5)
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        this.feedbackId = counter++;
        this.residentId = residentId;
        this.mealDescription = mealDescription;
        this.rating = rating;
        this.comment = comment;
        this.timestamp = LocalDateTime.now();
        this.resolved = false;
    }

    public int getFeedbackId() { return feedbackId; }
    public String getResidentId() { return residentId; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public boolean isResolved() { return resolved; }
    public void markResolved() { this.resolved = true; }

    @Override
    public String toString() {
        return String.format("[#%d] %s by %s | Rating: %d/5 | %s | %s",
                feedbackId, mealDescription, residentId, rating,
                comment, resolved ? "Resolved" : "Open");
    }
}