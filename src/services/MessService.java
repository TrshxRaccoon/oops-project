package services;

import hostel.HostelManager;
import java.time.LocalDate;

public class MessService {

    private static final int MAX_SUBS = 100;
    private static final int MAX_FEEDBACK = 200;

    private HostelManager hostelManager;

    private MessMenu[][] weeklyMenu;

    private String[] subscribedIds;
    private MealSubscription[] subscriptions;
    private int subCount;

    private MessFeedback[] feedbackList;
    private int feedbackCount;

    public MessService(HostelManager hostelManager) 
    {
        this.hostelManager = hostelManager;
        this.weeklyMenu = new MessMenu[7][4];
        this.subscribedIds = new String[MAX_SUBS];
        this.subscriptions = new MealSubscription[MAX_SUBS];
        this.feedbackList = new MessFeedback[MAX_FEEDBACK];
        this.subCount = 0;
        this.feedbackCount = 0;
        loadDefaultMenu();
    }

    private boolean residentExists(String residentId) 
    {
        if (hostelManager.getResident(residentId) == null) 
        {
            System.out.println("Resident not found: " + residentId);
            return false;
        }
        return true;
    }

    private int findSubIndex(String residentId)
    {
        for (int i = 0; i < subCount; i++) 
        {
            if (subscribedIds[i] != null && subscribedIds[i].equals(residentId))
                return i;
        }
        return -1;
    }

    private void loadDefaultMenu() 
    {
        weeklyMenu[0][0] = new MessMenu("MON", "BREAKFAST", "Poha, Chai", true);
        weeklyMenu[0][1] = new MessMenu("MON", "LUNCH", "Dal, Rice, Roti", true);
        weeklyMenu[0][2] = new MessMenu("MON", "SNACKS", "Samosa", true);
        weeklyMenu[0][3] = new MessMenu("MON", "DINNER", "Paneer", true);
    }

    public void displayFullWeeklyMenu() 
    {

        String[] days = {"MON","TUE","WED","THU","FRI","SAT","SUN"};
        String[] meals = {"BREAKFAST","LUNCH","SNACKS","DINNER"};

        System.out.println("\n--- WEEKLY MENU ---");

        for (int i = 0; i < 7; i++) 
        {
            System.out.println("\n" + days[i]);
            for (int j = 0; j < 4; j++) 
            {
                if (weeklyMenu[i][j] != null)
                    System.out.println(weeklyMenu[i][j]);
                else
                    System.out.println(meals[j] + ": Not set");
            }
        }
    }

    public void subscribe(String residentId, String plan, LocalDate start, LocalDate end) 
    {
        if (!residentExists(residentId)) return;
        int idx = findSubIndex(residentId);

        if (idx != -1 && subscriptions[idx].isActive()) 
        {
            System.out.println("Already subscribed.");
            return;
        }

        if (subCount >= MAX_SUBS)
        {
            System.out.println("Subscription full");
            return;
        }

        MealSubscription sub = new MealSubscription(residentId, plan, start, end);

        if (idx != -1)
        {
            subscriptions[idx] = sub;
        }
        else
        {
            subscribedIds[subCount] = residentId;
            subscriptions[subCount] = sub;
            subCount++;
        }
        System.out.println("Subscription added.");
    }

    public void subscribeCustom(String residentId, LocalDate start, LocalDate end,
                                boolean b, boolean l, boolean s, boolean d) 
    {

        if (!residentExists(residentId)) return;
        if (subCount >= MAX_SUBS)
        {
            System.out.println("Subscription full.");
            return;
        }

        MealSubscription sub = new MealSubscription(residentId, "CUSTOM", start, end);
        sub.setCustomMeals(b, l, s, d);

        int idx = findSubIndex(residentId);

        if (idx != -1) 
        {
            subscriptions[idx] = sub;
        }
        else
        {
            subscribedIds[subCount] = residentId;
            subscriptions[subCount] = sub;
            subCount++;
        }

        System.out.println("Custom subscription added.");
    }

    public void submitFeedback(String residentId, String meal, int rating, String comment) 
    {
        if (!residentExists(residentId)) return;
        if (feedbackCount >= MAX_FEEDBACK)
        {
            System.out.println("Feedback full");
            return;
        }
        feedbackList[feedbackCount] = new MessFeedback(residentId, meal, rating, comment);
        System.out.println("Feedback added.");
        feedbackCount++;
    }

    public void viewAllFeedback()
    {

        if (feedbackCount == 0) 
        {
            System.out.println("No feedback");
            return;
        }

        int total = 0;
        for (int i = 0; i < feedbackCount; i++)
        {
            System.out.println(feedbackList[i]);
            total += feedbackList[i].getRating();
        }
        System.out.println("Average: " + (total / (double) feedbackCount));
    }

    public void resolveFeedback(int id) 
    {
        for (int i = 0; i < feedbackCount; i++) 
        {
            if (feedbackList[i].getFeedbackId() == id) 
            {
                feedbackList[i].markResolved();
                System.out.println("Resolved.");
                return;
            }
        }
        System.out.println("Not found.");
    }
}