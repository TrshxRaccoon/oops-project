package services;
import java.time.LocalDate;

public class MealSubscription 
{
    private String residentId; //ID of the resident associated with the meal subscription
    private boolean[] mealFlags; //breakfast, lunch, snacks, dinner
    private LocalDate startDate;//start date of the meal subscription
    private LocalDate endDate;//end date of the meal subscription
    private boolean active;//indicates if the subscription is currently active

    public MealSubscription(String residentId, LocalDate startDate, LocalDate endDate) 
    {
        this.residentId = residentId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = true;
        this.mealFlags = new boolean[]{true, true, true, true};
    }

    public String getResidentId()  
    { 
        return residentId;
    }

    public boolean[] getMealFlags()
    { 
        return mealFlags;
    }

    public LocalDate getStartDate()
    { 
        return startDate; 
    }
    
    public LocalDate getEndDate()
    { 
        return endDate;
    }

    public boolean isActive() 
    {
        return active && !LocalDate.now().isAfter(endDate);
    }

    public void deactivate() 
    {
        active = false;
    }

    public String toString() 
    {
        return "Resident: " + residentId + " | From: " + startDate + " | To: " + endDate + " | Active: " + isActive();
    }
}