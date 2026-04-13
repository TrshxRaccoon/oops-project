package services;

public class MessMenu {
    public enum Day { MON, TUE, WED, THU, FRI, SAT, SUN }
    public enum MealType { BREAKFAST, LUNCH, SNACKS, DINNER }

    private Day day;
    private MealType mealType;
    private String description;   // e.g. "Idli, Sambar, Chutney"
    private boolean isVegOnly;

    public MessMenu(Day day, MealType mealType, String description, boolean isVegOnly) {
        this.day = day;
        this.mealType = mealType;
        this.description = description;
        this.isVegOnly = isVegOnly;
    }

    public Day getDay() { return day; }
    public MealType getMealType() { return mealType; }
    public String getDescription() { return description; }
    public boolean isVegOnly() { return isVegOnly; }

    public void setDescription(String description) { this.description = description; }
    public void setVegOnly(boolean vegOnly) { isVegOnly = vegOnly; }

    @Override
    public String toString() {
        return String.format("[%s | %s] %s %s",
                day, mealType, description, isVegOnly ? "(Veg)" : "(Non-Veg)");
    }
}