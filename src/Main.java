import hostel.HostelManager;
import services.MessService;
import services.PaymentServices;
import services.MealSubscription.Plan;
import services.Fee.FeeType;
import services.Payment.PaymentMode;

import java.time.LocalDate;
import java.util.*;
import users.Resident;

public class Main {

    public static int getInt(Scanner sc, String message) {
        while (true) {
            try {
                System.out.print(message);
                int value = sc.nextInt();
                sc.nextLine();
                return value;
            } catch (java.util.InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                sc.nextLine();
            }
        }
    }

    public static double getDouble(Scanner sc, String message) {
        while (true) {
            try {
                System.out.print(message);
                double value = sc.nextDouble();
                sc.nextLine();
                return value;
            } catch (java.util.InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                sc.nextLine();
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HostelManager hm = new HostelManager();
        MessService mess = new MessService(hm);
        PaymentServices payments = new PaymentServices(hm);

        System.out.println("=== Smart Hostel Management System ===");

        while (true) {
            System.out.println("\n--- Resident & Room ---");
            System.out.println("1.  Add Resident");
            System.out.println("2.  Add Room");
            System.out.println("3.  Allocate Room");
            System.out.println("4.  Vacate Room");
            System.out.println("5.  Show Residents");
            System.out.println("6.  Show Rooms");
            System.out.println("--- Mess ---");
            System.out.println("8.  View Weekly Menu");
            System.out.println("9.  Subscribe to Mess");
            System.out.println("10. Cancel Subscription");
            System.out.println("11. View Subscription");
            System.out.println("12. Submit Mess Feedback");
            System.out.println("13. View All Feedback");
            System.out.println("--- Payments ---");
            System.out.println("14. Add Fee");
            System.out.println("15. Make Payment");
            System.out.println("16. View Fees for Resident");
            System.out.println("17. Payment Reminders");
            System.out.println("18. Financial Report");
            System.out.println("---");
            System.out.println("7.  Exit");

            int choice = getInt(sc, "\nEnter choice: ");

            switch (choice) {

                // ── RESIDENT & ROOM ──────────────────────────────
                case 1:
                    System.out.print("Enter Resident ID: ");
                    String id = sc.next();
                    System.out.print("Enter Name: ");
                    String name = sc.next();
                    Resident r = new Resident(id, name);
                    hm.addResident(r);
                    System.out.println("Resident added!");
                    break;

                case 2:
                    int roomNo = getInt(sc, "Enter Room Number: ");
                    int cap = getInt(sc, "Enter Capacity: ");
                    hm.addRoom(roomNo, cap);
                    System.out.println("Room added!");
                    break;

                case 3:
                    System.out.print("Enter Resident ID: ");
                    String rid = sc.next();
                    int rno = getInt(sc, "Enter Room Number: ");
                    hm.allocateRoom(rid, rno);
                    break;

                case 4:
                    System.out.print("Enter Resident ID: ");
                    String vid = sc.next();
                    hm.vacateRoom(vid);
                    break;

                case 5:
                    hm.showResidents();
                    break;

                case 6:
                    hm.showRooms();
                    break;

                // ── MESS ─────────────────────────────────────────
                case 8:
                    mess.displayFullWeeklyMenu();
                    break;

                case 9:
                    System.out.print("Resident ID: ");
                    String subId = sc.next();
                    System.out.println("Plans:");
                    System.out.println("  1. FULL_BOARD      (Breakfast + Lunch + Snacks + Dinner)");
                    System.out.println("  2. LUNCH_DINNER    (Lunch + Dinner)");
                    System.out.println("  3. BREAKFAST_ONLY  (Breakfast only)");
                    System.out.println("  4. CUSTOM          (Choose individual meals)");
                    int planChoice = getInt(sc, "Choose plan: ");
                    if (planChoice < 1 || planChoice > 4) {
                        System.out.println("Invalid plan choice.");
                        break;
                    }
                    Plan[] plans = Plan.values();
                    Plan chosen = plans[planChoice - 1];

                    if (chosen == Plan.CUSTOM) {
                        System.out.println("Select meals (y/n):");
                        System.out.print("  Breakfast? "); boolean b = sc.next().equalsIgnoreCase("y");
                        System.out.print("  Lunch?     "); boolean l = sc.next().equalsIgnoreCase("y");
                        System.out.print("  Snacks?    "); boolean s = sc.next().equalsIgnoreCase("y");
                        System.out.print("  Dinner?    "); boolean d = sc.next().equalsIgnoreCase("y");
                        int months = getInt(sc, "Duration in months: ");
                        mess.subscribeCustom(subId, LocalDate.now(),
                                LocalDate.now().plusMonths(months), b, l, s, d);
                    } else {
                        int months = getInt(sc, "Duration in months: ");
                        mess.subscribe(subId, chosen,
                                LocalDate.now(), LocalDate.now().plusMonths(months));
                    }
                    break;

                case 10:
                    System.out.print("Resident ID: ");
                    mess.cancelSubscription(sc.next());
                    break;

                case 11:
                    System.out.print("Resident ID: ");
                    mess.viewSubscription(sc.next());
                    break;

                case 12:
                    System.out.print("Resident ID: ");
                    String fbResId = sc.next();
                    sc.nextLine(); // flush
                    System.out.print("Meal description (e.g. Monday Dinner): ");
                    String mealDesc = sc.nextLine();
                    int rating = getInt(sc, "Rating (1-5): ");
                    sc.nextLine(); // flush
                    System.out.print("Comment: ");
                    String comment = sc.nextLine();
                    mess.submitFeedback(fbResId, mealDesc, rating, comment);
                    break;

                case 13:
                    System.out.println("1. View All Feedback");
                    System.out.println("2. View Open Feedback");
                    System.out.println("3. Resolve Feedback");
                    int fbChoice = getInt(sc, "Choose: ");
                    if (fbChoice == 1) mess.viewAllFeedback();
                    else if (fbChoice == 2) mess.viewOpenFeedback();
                    else if (fbChoice == 3) {
                        int fbId = getInt(sc, "Enter Feedback ID to resolve: ");
                        mess.resolveFeedback(fbId);
                    } else System.out.println("Invalid choice.");
                    break;

                // ── PAYMENTS ─────────────────────────────────────
                case 14:
                    System.out.print("Resident ID: ");
                    String feeResId = sc.next();
                    System.out.println("Fee Types:");
                    System.out.println("  1. HOSTEL_RENT");
                    System.out.println("  2. MESS");
                    System.out.println("  3. MAINTENANCE");
                    System.out.println("  4. SECURITY_DEPOSIT");
                    System.out.println("  5. MISCELLANEOUS");
                    int ftChoice = getInt(sc, "Choose type: ");
                    if (ftChoice < 1 || ftChoice > 5) { System.out.println("Invalid type."); break; }
                    double amt = getDouble(sc, "Amount (₹): ");
                    int dueDays = getInt(sc, "Due in how many days from today: ");
                    payments.addFee(feeResId, FeeType.values()[ftChoice - 1],
                            amt, LocalDate.now().plusDays(dueDays));
                    break;

                case 15:
                    payments.displayAllFees();
                    int feeId = getInt(sc, "Enter Fee ID to pay: ");
                    double payAmt = getDouble(sc, "Amount (₹): ");
                    System.out.println("Payment Mode:");
                    System.out.println("  1. CASH");
                    System.out.println("  2. UPI");
                    System.out.println("  3. BANK_TRANSFER");
                    System.out.println("  4. CARD");
                    int modeChoice = getInt(sc, "Choose mode: ");
                    if (modeChoice < 1 || modeChoice > 4) { System.out.println("Invalid mode."); break; }
                    System.out.print("Transaction Reference (receipt/UPI ID): ");
                    String ref = sc.next();
                    payments.makePayment(feeId, payAmt, PaymentMode.values()[modeChoice - 1], ref);
                    break;

                case 16:
                    System.out.print("Resident ID: ");
                    String viewFeeId = sc.next();
                    payments.displayFeesForResident(viewFeeId);
                    payments.displayPaymentHistory(viewFeeId);
                    break;

                case 17:
                    int days = getInt(sc, "Show reminders for dues within how many days: ");
                    payments.generatePaymentReminders(days);
                    break;

                case 18:
                    payments.generateFinancialReport();
                    break;

                // ── EXIT ─────────────────────────────────────────
                case 7:
                    System.out.println("Exiting... Goodbye!");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice! Please enter a number from the menu.");
            }
        }
    }
}