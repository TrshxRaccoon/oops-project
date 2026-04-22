package services;

public class MessMenu 
{
    private String day;
    private String mealType;
    private String description;

    public MessMenu(String day, String mealType, String description) 
    {
        this.day = day;
        this.mealType = mealType;
        this.description = description;
    }

    public String getDay()         { return day; }
    public String getMealType()    { return mealType; }
    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() 
    {
        return mealType + ": " + description;
    }
}