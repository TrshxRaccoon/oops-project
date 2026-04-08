import java.time.LocalDate;

/**
 * Represents a feedback/complaint submitted about the mess.
 * Demonstrates: Overloaded constructors, Wrapper types
 */
public class Feedback {

    public enum Category { FOOD_QUALITY, HYGIENE, SERVICE, QUANTITY, OTHER }

    private static int feedbackCounter = 300;

    private String feedbackId;      // Unique ID
    private String residentId;      // Who submitted this
    private String residentName;    // Resident name
    private Category category;      // Type of feedback
    private String message;         // Detailed feedback text
    private Integer rating;         // Rating out of 5 (Integer wrapper)
    private String date;            // Date of submission
    private Boolean isResolved;     // Whether admin has resolved this

    // Constructor 1: Feedback with just message
    public Feedback(String residentId, String residentName, String message) {
        this.feedbackId = "FB-" + feedbackCounter++;
        this.residentId = residentId;
        this.residentName = residentName;
        this.message = message;
        this.category = Category.OTHER;
        this.rating = Integer.valueOf(3); // neutral default
        this.date = LocalDate.now().toString();
        this.isResolved = Boolean.FALSE;
    }

    // Constructor 2: Full feedback with rating and category
    public Feedback(String residentId, String residentName, Category category,
                    String message, int rating) {
        this.feedbackId = "FB-" + feedbackCounter++;
        this.residentId = residentId;
        this.residentName = residentName;
        this.category = category;
        this.message = message;
        this.rating = Integer.valueOf(rating); // Integer wrapper
        this.date = LocalDate.now().toString();
        this.isResolved = Boolean.FALSE;
    }

    public void resolve() { this.isResolved = Boolean.TRUE; }

    // Getters
    public String getFeedbackId()   { return feedbackId; }
    public String getResidentId()   { return residentId; }
    public Category getCategory()   { return category; }
    public String getMessage()      { return message; }
    public Integer getRating()      { return rating; }
    public Boolean isResolved()     { return isResolved; }

    public void displayFeedback() {
        System.out.println("[" + feedbackId + "] " + residentName
                + " | Category: " + category
                + " | Rating: " + rating + "/5"
                + " | Resolved: " + isResolved
                + "\n   Message: " + message);
    }
}