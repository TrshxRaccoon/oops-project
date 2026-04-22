package services;

import java.time.LocalDate;

public class MealSubscription 
{
    private String residentId;
    private boolean[] mealFlags; // [breakfast, lunch, snacks, dinner]
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;

    public MealSubscription(String residentId, LocalDate startDate, LocalDate endDate) 
    {
        this.residentId = residentId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = true;
        this.mealFlags = new boolean[]{true, true, true, true};
    }

    public String getResidentId()  { return residentId; }
    public boolean[] getMealFlags(){ return mealFlags; }
    public LocalDate getStartDate(){ return startDate; }
    public LocalDate getEndDate()  { return endDate; }

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