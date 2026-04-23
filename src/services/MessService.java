package services;
import hostel.HostelManager;
import java.time.LocalDate;

public class MessService 
{

    private static final int MAX_SUBS     = 100;
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
        this.hostelManager  = hostelManager;
        this.weeklyMenu     = new MessMenu[7][4];
        this.subscribedIds  = new String[MAX_SUBS];
        this.subscriptions  = new MealSubscription[MAX_SUBS];
        this.feedbackList   = new MessFeedback[MAX_FEEDBACK];
        this.subCount       = 0;
        this.feedbackCount  = 0;
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
        weeklyMenu[0][0] = new MessMenu("MON", "BREAKFAST", "Poha, Chai");
        weeklyMenu[0][1] = new MessMenu("MON", "LUNCH",     "Dal Tadka, Rice, Roti");
        weeklyMenu[0][2] = new MessMenu("MON", "SNACKS",    "Samosa, Chai");
        weeklyMenu[0][3] = new MessMenu("MON", "DINNER",    "Paneer Butter Masala, Naan");

        weeklyMenu[1][0] = new MessMenu("TUE", "BREAKFAST", "Idli, Sambar, Chutney");
        weeklyMenu[1][1] = new MessMenu("TUE", "LUNCH",     "Rajma, Jeera Rice, Roti");
        weeklyMenu[1][2] = new MessMenu("TUE", "SNACKS",    "Bread Pakora, Tea");
        weeklyMenu[1][3] = new MessMenu("TUE", "DINNER",    "Chicken Curry, Rice, Roti");

        weeklyMenu[2][0] = new MessMenu("WED", "BREAKFAST", "Aloo Paratha, Curd");
        weeklyMenu[2][1] = new MessMenu("WED", "LUNCH",     "Chole, Rice, Roti");
        weeklyMenu[2][2] = new MessMenu("WED", "SNACKS",    "Vada Pav, Tea");
        weeklyMenu[2][3] = new MessMenu("WED", "DINNER",    "Dal Makhani, Roti, Rice");

        weeklyMenu[3][0] = new MessMenu("THU", "BREAKFAST", "Upma, Chai");
        weeklyMenu[3][1] = new MessMenu("THU", "LUNCH",     "Mix Veg, Rice, Roti");
        weeklyMenu[3][2] = new MessMenu("THU", "SNACKS",    "Maggi, Tea");
        weeklyMenu[3][3] = new MessMenu("THU", "DINNER",    "Egg Curry, Rice, Roti");

        weeklyMenu[4][0] = new MessMenu("FRI", "BREAKFAST", "Puri, Aloo Sabji");
        weeklyMenu[4][1] = new MessMenu("FRI", "LUNCH",     "Kadhi, Rice, Roti");
        weeklyMenu[4][2] = new MessMenu("FRI", "SNACKS",    "Pakora, Chai");
        weeklyMenu[4][3] = new MessMenu("FRI", "DINNER",    "Mutton Curry, Rice, Roti");

        weeklyMenu[5][0] = new MessMenu("SAT", "BREAKFAST", "Dosa, Sambar, Chutney");
        weeklyMenu[5][1] = new MessMenu("SAT", "LUNCH",     "Palak Paneer, Rice, Roti");
        weeklyMenu[5][2] = new MessMenu("SAT", "SNACKS",    "Cake, Coffee");
        weeklyMenu[5][3] = new MessMenu("SAT", "DINNER",    "Biryani, Raita");

        weeklyMenu[6][0] = new MessMenu("SUN", "BREAKFAST", "Puri, Halwa");
        weeklyMenu[6][1] = new MessMenu("SUN", "LUNCH",     "Special Thali");
        weeklyMenu[6][2] = new MessMenu("SUN", "SNACKS",    "Ice Cream");
        weeklyMenu[6][3] = new MessMenu("SUN", "DINNER",    "Paneer Tikka, Naan, Rice");
    }

    public void displayFullWeeklyMenu() 
    {
        String[] days  = {"MON","TUE","WED","THU","FRI","SAT","SUN"};
        String[] meals = {"BREAKFAST","LUNCH","SNACKS","DINNER"};
        System.out.println("\nWEEKLY MENU:");

        for (int i = 0; i < 7; i++) 
        {
            System.out.println("\n" + days[i]);
            for (int j = 0; j < 4; j++) 
            {
                if (weeklyMenu[i][j] != null)
                    System.out.println("  " + weeklyMenu[i][j]);
                else
                    System.out.println("  " + meals[j] + ": Not set");
            }
        }
    }

    public void updateMenuItem(int day, int meal, String description) 
    {
        if (day < 0 || day > 6 || meal < 0 || meal > 3) 
        {
            System.out.println("Invalid day or meal index.");
            return;
        }
        String[] days  = {"MON","TUE","WED","THU","FRI","SAT","SUN"};
        String[] meals = {"BREAKFAST","LUNCH","SNACKS","DINNER"};
        weeklyMenu[day][meal] = new MessMenu(days[day], meals[meal], description);
        System.out.println("Menu updated: " + days[day] + " " + meals[meal] + " -> " + description);
    }

    public void subscribe(String residentId, LocalDate start, LocalDate end) 
    {
        if (!residentExists(residentId)) return;
        int idx = findSubIndex(residentId);

        if (idx != -1 && subscriptions[idx].isActive()) 
        {
            System.out.println("Already subscribed; ends on: " + subscriptions[idx].getEndDate());
            return;
        }
        if (subCount >= MAX_SUBS) 
        {
            System.out.println("Subscription list full");
            return;
        }

        MealSubscription sub = new MealSubscription(residentId, start, end);

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
        System.out.println("Subscription added: " + sub);
    }

    public void cancelSubscription(String residentId) 
    {
        int idx = findSubIndex(residentId);
        if (idx != -1 && subscriptions[idx].isActive()) 
        {
            subscriptions[idx].deactivate();
            System.out.println("Subscription cancelled for: " + residentId);
        } 
        else 
        {
            System.out.println("No active subscription found for: " + residentId);
        }
    }

    public void viewSubscription(String residentId) 
    {
        int idx = findSubIndex(residentId);
        if (idx != -1)
            System.out.println(subscriptions[idx]);
        else
            System.out.println("No subscription found for:" + residentId);
    }

    public void submitFeedback(String residentId, String meal, int rating, String comment) 
    {
        if (!residentExists(residentId))
        {
            return;
        }

        if (feedbackCount >= MAX_FEEDBACK) 
        {
            System.out.println("Feedback list is full");
            return;
        }

        try 
        {
            feedbackList[feedbackCount] = new MessFeedback(residentId, meal, rating, comment);
            System.out.println("Feedback added.");
            feedbackCount++;
        } 
        catch (IllegalArgumentException e) 
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void viewAllFeedback() 
    {
        if (feedbackCount == 0) 
        {
            System.out.println("No feedback yet.");
            return;
        }

        int total = 0;
        for (int i = 0; i < feedbackCount; i++) 
        {
            System.out.println(feedbackList[i]);
            total += feedbackList[i].getRating();
        }
        
        System.out.println("Average Rating: " + (total / (double) feedbackCount) + "/5");
    }

    public void viewOpenFeedback() 
    {
        System.out.println("\nOPEN FEEDBACK:");
        boolean any = false;
        for (int i = 0; i < feedbackCount; i++) 
        {
            if (!feedbackList[i].isResolved()) 
            {
                System.out.println(feedbackList[i]);
                any = true;
            }
        }
        if (!any) System.out.println("No open feedback.");
    }

    public void resolveFeedback(int id) 
    {
        for (int i = 0; i < feedbackCount; i++) 
        {
            if (feedbackList[i].getFeedbackId() == id) 
            {
                feedbackList[i].markResolved();
                System.out.println("Feedback #" + id + " marked as resolved.");
                return;
            }
        }
        System.out.println("Feedback ID not found: " + id);
    }
}