package services;

import java.time.LocalDate;

public class MealSubscription {

    private String residentId;
    private String plan;   // simple string instead of enum
    private boolean[] mealFlags; // [breakfast, lunch, snacks, dinner]
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;

    public MealSubscription(String residentId, String plan, LocalDate startDate, LocalDate endDate) {
        this.residentId = residentId;
        this.plan = plan;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = true;
        this.mealFlags = getDefaultFlags(plan);
    }

    private boolean[] getDefaultFlags(String plan) {

        // FULL_BOARD
        if (plan.equals("FULL_BOARD")) {
            return new boolean[]{true, true, true, true};
        }

        // LUNCH_DINNER
        else if (plan.equals("LUNCH_DINNER")) {
            return new boolean[]{false, true, false, true};
        }

        // BREAKFAST_ONLY
        else if (plan.equals("BREAKFAST_ONLY")) {
            return new boolean[]{true, false, false, false};
        }

        // CUSTOM or anything else
        else {
            return new boolean[]{false, false, false, false};
        }
    }

    public void setCustomMeals(boolean breakfast, boolean lunch, boolean snacks, boolean dinner) {

        if (plan.equals("CUSTOM")) {
            mealFlags = new boolean[]{breakfast, lunch, snacks, dinner};
        } else {
            System.out.println("Only CUSTOM plan can set individual meals.");
        }
    }

    public String getResidentId() { return residentId; }
    public String getPlan() { return plan; }
    public boolean[] getMealFlags() { return mealFlags; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }

    public boolean isActive() {
        return active && !LocalDate.now().isAfter(endDate);
    }

    public void deactivate() {
        active = false;
    }

    @Override
    public String toString() {
        return "Resident: " + residentId +
                " | Plan: " + plan +
                " | From: " + startDate +
                " | To: " + endDate +
                " | Active: " + isActive();
    }
}