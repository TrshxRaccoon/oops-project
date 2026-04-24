package services;

public class MessFeedback 
{
    private static int counter = 1;//counter to generate unique feedback IDs
    private int feedbackId;//unique feedback ID
    private String residentId;//ID of the resident providing the feedback
    private String mealDescription;//description of the meal being reviewed
    private int rating;//rating for the meal (1-5)
    private String comment;//comment provided by the resident
    private boolean resolved;//indicates if the feedback has been addressed by the mess manager

    public MessFeedback(String residentId, String mealDescription, int rating, String comment) 
    {
        if (rating < 1 || rating > 5)
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        this.feedbackId = counter++;
        this.residentId = residentId;
        this.mealDescription = mealDescription;
        this.rating = rating;
        this.comment = comment;
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

    public boolean isResolved()
    {
        return resolved;
    }

    public void markResolved()
    { 
        this.resolved = true;
    }

    public String toString() 
    {
        return "[#" + feedbackId + "] " + mealDescription + " by " + residentId + " | Rating: " + rating + "/5 | " + comment;
    }
}