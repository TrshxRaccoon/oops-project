package services;

import java.time.LocalDate;

public class MealSubscription {
    public enum Plan { FULL_BOARD, LUNCH_DINNER, BREAKFAST_ONLY, CUSTOM }

    private String residentId;
    private Plan plan;
    private boolean[] mealFlags; // [breakfast, lunch, snacks, dinner]
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;

    // Standard plan constructor
    public MealSubscription(String residentId, Plan plan, LocalDate startDate, LocalDate endDate) {
        this.residentId = residentId;
        this.plan = plan;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = true;
        this.mealFlags = getDefaultFlags(plan);
    }

    private boolean[] getDefaultFlags(Plan plan) {
        // [breakfast, lunch, snacks, dinner]
        switch (plan) {
            case FULL_BOARD:
                return new boolean[]{true,  true,  true,  true};
            case LUNCH_DINNER:
                return new boolean[]{false, true,  false, true};
            case BREAKFAST_ONLY:
                return new boolean[]{true,  false, false, false};
            case CUSTOM:
                return new boolean[]{false, false, false, false};
            default:
                return new boolean[]{false, false, false, false};
        }
    }

    public void setCustomMeals(boolean breakfast, boolean lunch, boolean snacks, boolean dinner) {
        if (plan == Plan.CUSTOM) {
            mealFlags = new boolean[]{breakfast, lunch, snacks, dinner};
        } else {
            System.out.println("Switch plan to CUSTOM first to set individual meals.");
        }
    }

    public String getResidentId() { return residentId; }
    public Plan getPlan() { return plan; }
    public boolean[] getMealFlags() { return mealFlags; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public boolean isActive() { return active && !LocalDate.now().isAfter(endDate); }
    public void deactivate() { this.active = false; }

    @Override
    public String toString() {
        return String.format("Resident: %s | Plan: %s | %s to %s | Active: %s",
                residentId, plan, startDate, endDate, isActive());
    }
}