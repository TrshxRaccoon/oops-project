package services;

import hostel.HostelManager;
import users.Resident;
import services.MessMenu.Day;
import services.MessMenu.MealType;
import services.MealSubscription.Plan;

import java.time.LocalDate;
import java.util.*;

public class MessService {

    private final HostelManager hostelManager;
    private final Map<String, MessMenu> weeklyMenu;        // "MON_BREAKFAST" → MessMenu
    private final Map<String, MealSubscription> subscriptions; // residentId → subscription
    private final List<MessFeedback> feedbackList;

    public MessService(HostelManager hostelManager) {
        this.hostelManager = hostelManager;
        this.weeklyMenu = new LinkedHashMap<>();
        this.subscriptions = new HashMap<>();
        this.feedbackList = new ArrayList<>();
        loadDefaultMenu();
    }

    // ─────────────────────────────────────────────
    //  INTERNAL HELPER
    // ─────────────────────────────────────────────

    private boolean residentExists(String residentId) {
        if (hostelManager.getResident(residentId) == null) {
            System.out.println("Resident not found: " + residentId);
            return false;
        }
        return true;
    }

    // ─────────────────────────────────────────────
    //  MENU MANAGEMENT
    // ─────────────────────────────────────────────

    private void loadDefaultMenu() {
        addMenuItem(Day.MON, MealType.BREAKFAST, "Poha, Chai", true);
        addMenuItem(Day.MON, MealType.LUNCH,     "Dal, Rice, Roti, Sabji", true);
        addMenuItem(Day.MON, MealType.SNACKS,    "Samosa, Chai", true);
        addMenuItem(Day.MON, MealType.DINNER,    "Paneer Butter Masala, Naan", true);
        addMenuItem(Day.TUE, MealType.BREAKFAST, "Idli, Sambar, Chutney", true);
        addMenuItem(Day.TUE, MealType.LUNCH,     "Rajma, Rice, Roti", true);
        addMenuItem(Day.TUE, MealType.SNACKS,    "Biscuits, Tea", true);
        addMenuItem(Day.TUE, MealType.DINNER,    "Chicken Curry, Rice, Roti", false);
        // Add remaining days similarly...
    }

    public void addMenuItem(Day day, MealType mealType, String description, boolean isVegOnly) {
        String key = day + "_" + mealType;
        weeklyMenu.put(key, new MessMenu(day, mealType, description, isVegOnly));
        System.out.println("Menu set: " + day + " " + mealType + " → " + description);
    }

    public void updateMenuItem(Day day, MealType mealType, String newDescription, boolean isVegOnly) {
        String key = day + "_" + mealType;
        if (weeklyMenu.containsKey(key)) {
            weeklyMenu.get(key).setDescription(newDescription);
            weeklyMenu.get(key).setVegOnly(isVegOnly);
            System.out.println("Updated: " + key);
        } else {
            addMenuItem(day, mealType, newDescription, isVegOnly);
        }
    }

    public void displayFullWeeklyMenu() {
        System.out.println("\n========== WEEKLY MESS MENU ==========");
        for (Day day : Day.values()) {
            System.out.println("\n--- " + day + " ---");
            for (MealType meal : MealType.values()) {
                MessMenu item = weeklyMenu.get(day + "_" + meal);
                if (item != null) System.out.println("  " + item);
                else             System.out.println("  [" + meal + "] Not scheduled");
            }
        }
        System.out.println("======================================");
    }

    public void displayMenuForDay(Day day) {
        System.out.println("\n--- Menu for " + day + " ---");
        for (MealType meal : MealType.values()) {
            MessMenu item = weeklyMenu.get(day + "_" + meal);
            if (item != null) System.out.println("  " + item);
            else              System.out.println("  [" + meal + "] Not scheduled");
        }
    }

    // ─────────────────────────────────────────────
    //  SUBSCRIPTION MANAGEMENT
    // ─────────────────────────────────────────────

    public void subscribe(String residentId, Plan plan, LocalDate startDate, LocalDate endDate) {
        if (!residentExists(residentId)) return;

        MealSubscription existing = subscriptions.get(residentId);
        if (existing != null && existing.isActive()) {
            System.out.println("Resident " + residentId + " already has an active subscription.");
            System.out.println("Current plan: " + existing.getPlan() + " | Ends: " + existing.getEndDate());
            return;
        }
        MealSubscription sub = new MealSubscription(residentId, plan, startDate, endDate);
        subscriptions.put(residentId, sub);
        System.out.println("Subscribed successfully: " + sub);
    }

    public void subscribeCustom(String residentId, LocalDate start, LocalDate end,
                                boolean breakfast, boolean lunch, boolean snacks, boolean dinner) {
        if (!residentExists(residentId)) return;
        MealSubscription sub = new MealSubscription(residentId, Plan.CUSTOM, start, end);
        sub.setCustomMeals(breakfast, lunch, snacks, dinner);
        subscriptions.put(residentId, sub);
        System.out.println("Custom subscription set: " + sub);
    }

    public void cancelSubscription(String residentId) {
        MealSubscription sub = subscriptions.get(residentId);
        if (sub != null && sub.isActive()) {
            sub.deactivate();
            System.out.println("Subscription cancelled for: " + residentId);
        } else {
            System.out.println("No active subscription found for: " + residentId);
        }
    }

    public void viewSubscription(String residentId) {
        MealSubscription sub = subscriptions.get(residentId);
        if (sub != null) System.out.println(sub);
        else             System.out.println("No subscription found for: " + residentId);
    }

    public boolean hasActiveMealAccess(String residentId, MealType mealType) {
        MealSubscription sub = subscriptions.get(residentId);
        if (sub == null || !sub.isActive()) return false;
        boolean[] flags = sub.getMealFlags();
        switch (mealType) {
            case BREAKFAST:
                return flags[0];
            case LUNCH:
                return flags[1];
            case SNACKS:
                return flags[2];
            case DINNER:
                return flags[3];
            default:
                return false;
        }
    }

    // ─────────────────────────────────────────────
    //  FEEDBACK MANAGEMENT
    // ─────────────────────────────────────────────

    public void submitFeedback(String residentId, String mealDescription, int rating, String comment) {
        if (!residentExists(residentId)) return;
        try {
            MessFeedback fb = new MessFeedback(residentId, mealDescription, rating, comment);
            feedbackList.add(fb);
            System.out.println("Feedback recorded: " + fb);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void viewAllFeedback() {
        System.out.println("\n========== ALL MESS FEEDBACK ==========");
        if (feedbackList.isEmpty()) { System.out.println("No feedback yet."); return; }
        feedbackList.forEach(System.out::println);
        System.out.printf("Average Rating: %.1f/5%n", getAverageRating());
        System.out.println("=======================================");
    }

    public void viewOpenFeedback() {
        System.out.println("\n===== OPEN / UNRESOLVED FEEDBACK =====");
        boolean any = false;
        for (MessFeedback f : feedbackList) {
            if (!f.isResolved()) { System.out.println(f); any = true; }
        }
        if (!any) System.out.println("No open feedback.");
    }

    public void resolveFeedback(int feedbackId) {
        for (MessFeedback f : feedbackList) {
            if (f.getFeedbackId() == feedbackId) {
                f.markResolved();
                System.out.println("Feedback #" + feedbackId + " marked as resolved.");
                return;
            }
        }
        System.out.println("Feedback ID not found: " + feedbackId);
    }

    public double getAverageRating() {
        return feedbackList.stream().mapToInt(MessFeedback::getRating).average().orElse(0.0);
    }
}