package services;

public class MessMenu 
{
    private String day;//day of the week for the menu
    private String mealType;//breakfast, lunch, snacks, dinner
    private String description;//description of the meal for the given day and meal type

    public MessMenu(String day, String mealType, String description) 
    {
        this.day = day;
        this.mealType = mealType;
        this.description = description;
    }

    public String getDay()
    { 
        return day;
    }

    public String getMealType()
    {
        return mealType;
    }

    public String getDescription()
    { 
        return description;
    }

    public void setDescription(String description)
    { 
        this.description = description;
    }

    public String toString() 
    {
        return mealType + ":" + description;
    }
}