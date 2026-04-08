import java.util.HashMap;
import java.util.Map;

/**
 * Represents the weekly mess menu for the hostel.
 * Demonstrates: Overloaded methods, Wrapper types
 */
public class MessMenu {

    private String menuId;                          // Unique ID for the menu
    private String weekStartDate;                   // Week starting date
    private Map<String, String[]> dailyMenu;        // Day -> [Breakfast, Lunch, Snacks, Dinner]

    // Constructor 1: Empty menu (to be filled later)
    public MessMenu(String menuId, String weekStartDate) {
        this.menuId = menuId;
        this.weekStartDate = weekStartDate;
        this.dailyMenu = new HashMap<>();
        initializeDefaultMenu();
    }

    // Constructor 2: With a predefined daily menu map
    public MessMenu(String menuId, String weekStartDate, Map<String, String[]> dailyMenu) {
        this.menuId = menuId;
        this.weekStartDate = weekStartDate;
        this.dailyMenu = dailyMenu;
    }

    /** Sets up a default placeholder menu for all 7 days */
    private void initializeDefaultMenu() {
        String[] defaultMeals = {"Idli/Poha", "Dal Rice", "Tea & Snacks", "Roti Sabzi"};
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (String day : days) {
            dailyMenu.put(day, defaultMeals.clone());
        }
    }

    /**
     * Sets the menu for a specific day.
     * Overloaded setDayMenu — with varargs (vararg overloading)
     * @param day Day of the week
     * @param meals Vararg: breakfast, lunch, snacks, dinner
     */
    public void setDayMenu(String day, String... meals) {
        dailyMenu.put(day, meals);
    }

    /**
     * Overloaded setDayMenu — with a specific meal slot
     * @param day Day of the week
     * @param mealSlot 0=Breakfast, 1=Lunch, 2=Snacks, 3=Dinner
     * @param mealName What's being served
     */
    public void setDayMenu(String day, Integer mealSlot, String mealName) {
        String[] meals = dailyMenu.getOrDefault(day, new String[]{"", "", "", ""});
        if (mealSlot >= 0 && mealSlot < meals.length) {
            meals[mealSlot] = mealName;
        }
        dailyMenu.put(day, meals);
    }

    /** Displays the full weekly menu */
    public void displayWeeklyMenu() {
        System.out.println("\n===== Mess Menu (Week of " + weekStartDate + ") =====");
        String[] slots = {"Breakfast", "Lunch", "Snacks", "Dinner"};
        for (Map.Entry<String, String[]> entry : dailyMenu.entrySet()) {
            System.out.println("\n" + entry.getKey() + ":");
            String[] meals = entry.getValue();
            for (int i = 0; i < slots.length && i < meals.length; i++) {
                System.out.println("  " + slots[i] + ": " + meals[i]);
            }
        }
        System.out.println("=======================================\n");
    }

    /**
     * Displays menu for a specific day.
     * Overloaded displayMenu — by day name
     */
    public void displayMenu(String day) {
        System.out.println("\n--- Menu for " + day + " ---");
        String[] meals = dailyMenu.get(day);
        if (meals == null) {
            System.out.println("No menu available for " + day);
            return;
        }
        String[] slots = {"Breakfast", "Lunch", "Snacks", "Dinner"};
        for (int i = 0; i < slots.length && i < meals.length; i++) {
            System.out.println("  " + slots[i] + ": " + meals[i]);
        }
    }

    // Getters
    public String getMenuId()         { return menuId; }
    public String getWeekStartDate()  { return weekStartDate; }
}