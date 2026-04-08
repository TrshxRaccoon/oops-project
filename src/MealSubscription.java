/**
 * Tracks a resident's meal subscription preferences.
 * Demonstrates: Overloaded constructors, Boolean wrapper
 */
public class MealSubscription {

    private String subscriptionId;  // Unique ID
    private String residentId;      // Resident's ID
    private Boolean breakfast;      // Subscribed to breakfast?
    private Boolean lunch;          // Subscribed to lunch?
    private Boolean snacks;         // Subscribed to snacks?
    private Boolean dinner;         // Subscribed to dinner?
    private String dietType;        // "VEG" or "NON-VEG"

    private static int subCounter = 200;

    // Constructor 1: Subscribe to all meals (default)
    public MealSubscription(String residentId) {
        this.subscriptionId = "SUB-" + subCounter++;
        this.residentId = residentId;
        this.breakfast = Boolean.TRUE;
        this.lunch     = Boolean.TRUE;
        this.snacks    = Boolean.TRUE;
        this.dinner    = Boolean.TRUE;
        this.dietType  = "VEG";
    }

    // Constructor 2: Custom subscription
    public MealSubscription(String residentId, boolean breakfast, boolean lunch,
                             boolean snacks, boolean dinner, String dietType) {
        this.subscriptionId = "SUB-" + subCounter++;
        this.residentId = residentId;
        this.breakfast = Boolean.valueOf(breakfast);
        this.lunch     = Boolean.valueOf(lunch);
        this.snacks    = Boolean.valueOf(snacks);
        this.dinner    = Boolean.valueOf(dinner);
        this.dietType  = dietType;
    }

    // Getters
    public String getSubscriptionId() { return subscriptionId; }
    public String getResidentId()     { return residentId; }
    public Boolean hasBreakfast()     { return breakfast; }
    public Boolean hasLunch()         { return lunch; }
    public Boolean hasSnacks()        { return snacks; }
    public Boolean hasDinner()        { return dinner; }
    public String getDietType()       { return dietType; }

    // Setters
    public void setBreakfast(boolean b) { this.breakfast = Boolean.valueOf(b); }
    public void setLunch(boolean l)     { this.lunch = Boolean.valueOf(l); }
    public void setSnacks(boolean s)    { this.snacks = Boolean.valueOf(s); }
    public void setDinner(boolean d)    { this.dinner = Boolean.valueOf(d); }
    public void setDietType(String t)   { this.dietType = t; }

    public void displaySubscription() {
        System.out.println("[" + subscriptionId + "] Resident: " + residentId
                + " | Diet: " + dietType
                + " | Breakfast: " + breakfast
                + " | Lunch: " + lunch
                + " | Snacks: " + snacks
                + " | Dinner: " + dinner);
    }
}