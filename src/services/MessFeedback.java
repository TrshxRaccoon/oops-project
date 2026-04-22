package services;
import java.time.LocalDateTime;

public class MessFeedback 
{
    private static int counter = 1;
    private int feedbackId;
    private String residentId;
    private String mealDescription;
    private int rating;
    private String comment;
    private LocalDateTime timestamp;
    private boolean resolved;

    public MessFeedback(String residentId, String mealDescription, int rating, String comment) 
    {
        if (rating < 1 || rating > 5) 
        {
            System.out.println("Invalid rating; setting it to 3");
            rating = 3;
        }
        this.feedbackId = counter++;
        this.residentId = residentId;
        this.mealDescription = mealDescription;
        this.rating = rating;
        this.comment = comment;
        this.timestamp = LocalDateTime.now();
        this.resolved = false;
    }

    public int getFeedbackId() 
    { 
        return feedbackId;
    }

    public String getResidentId() 
    { 
        return residentId;
    }

    public int getRating() 
    { 
        return rating;
    }

    public String getComment() 
    { 
        return comment;
    }

    public LocalDateTime getTimestamp() 
    { 
        return timestamp;
    }

    public boolean isResolved() 
    { 
        return resolved;
    }

    public void markResolved()
    {
        resolved = true;
    }

    @Override
    public String toString() 
    {

        String status;
        if (resolved)
        {
            status = "Resolved";
        }
        else
        {
            status = "Open";
        }
        return "[#" + feedbackId + "] " + mealDescription + " by " + residentId + " | Rating: " + rating + "/5" + " | " + comment + " | " + status;
    }
}