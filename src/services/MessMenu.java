package services;

public class MessMenu {

    private String day;
    private String mealType;
    private String description;
    private boolean isVegOnly;

    public MessMenu(String day, String mealType, String description, boolean isVegOnly) {
        this.day = day;
        this.mealType = mealType;
        this.description = description;
        this.isVegOnly = isVegOnly;
    }

    public String toString() {
        return day + " | " + mealType + " | " + description +
                (isVegOnly ? " (Veg)" : " (Non-Veg)");
    }
}