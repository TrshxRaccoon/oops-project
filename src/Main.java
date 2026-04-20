import hostel.HostelManager;
import services.MessService;
import services.PaymentServices;

import java.time.*;
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
            } catch (Exception e) {
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
            } catch (Exception e) {
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
            System.out.println("1. Add Resident");
            System.out.println("2. Add Room");
            System.out.println("3. Allocate Room");
            System.out.println("4. Vacate Room");
            System.out.println("5. Show Residents");
            System.out.println("6. Show Rooms");

            System.out.println("\n--- Mess ---");
            System.out.println("8. View Weekly Menu");
            System.out.println("9. Subscribe to Mess");
            System.out.println("10. Submit Feedback");
            System.out.println("11. View Feedback");

            System.out.println("\n--- Payments ---");
            System.out.println("12. Add Fee");
            System.out.println("13. Make Payment");
            System.out.println("14. View Fees");
            System.out.println("15. Payment Reminders");
            System.out.println("16. Financial Report");

            System.out.println("\n7. Exit");

            int choice = getInt(sc, "\nEnter choice: ");

            switch (choice) {

                case 1:
                    System.out.print("Enter Resident ID: ");
                    String id = sc.next();
                    System.out.print("Enter Name: ");
                    String name = sc.next();
                    hm.addResident(new Resident(id, name));
                    System.out.println("Resident added.");
                    break;

                case 2:
                    int roomNo = getInt(sc, "Enter Room Number: ");
                    int cap = getInt(sc, "Enter Capacity: ");
                    hm.addRoom(roomNo, cap);
                    System.out.println("Room added.");
                    break;

                case 3:
                    System.out.print("Enter Resident ID: ");
                    String rid = sc.next();
                    int rno = getInt(sc, "Enter Room Number: ");
                    hm.allocateRoom(rid, rno);
                    break;

                case 4:
                    System.out.print("Enter Resident ID: ");
                    hm.vacateRoom(sc.next());
                    break;

                case 5:
                    hm.showResidents();
                    break;

                case 6:
                    hm.showRooms();
                    break;

                case 8:
                    mess.displayFullWeeklyMenu();
                    break;

                case 9:
                    System.out.print("Resident ID: ");
                    String subId = sc.next();

                    mess.subscribe(subId,
                            "DEFAULT",
                            LocalDate.now(),
                            LocalDate.now().plusMonths(1));

                    System.out.println("Subscribed for 1 month.");
                    break;

                case 10:
                    System.out.print("Resident ID: ");
                    String fbId = sc.next();
                    sc.nextLine();

                    System.out.print("Meal Name [Dish]: ");
                    String meal = sc.nextLine();

                    int rating = getInt(sc, "Rating (1-5): ");

                    System.out.print("Comment: ");
                    String comment = sc.nextLine();

                    mess.submitFeedback(fbId, meal, rating, comment);
                    break;

                case 11:
                    mess.viewAllFeedback();
                    break;

                case 12:
                    System.out.print("Resident ID: ");
                    String feeResId = sc.next();

                    // ✅ CHANGED HERE
                    String[] feeTypes = { "HOSTEL", "TUITION", "MESS" };

                    for (int i = 0; i < feeTypes.length; i++) {
                        System.out.println((i + 1) + ". " + feeTypes[i]);
                    }

                    int ftChoice = getInt(sc, "Choose type: ");
                    if (ftChoice < 1 || ftChoice > 3) {   // ✅ CHANGED
                        System.out.println("Invalid.");
                        break;
                    }

                    double amt = getDouble(sc, "Amount: ");
                    int due = getInt(sc, "Due in days: ");

                    payments.addFee(feeResId,
                            feeTypes[ftChoice - 1],
                            amt,
                            LocalDate.now().plusDays(due));
                    break;

                case 13:
                    payments.displayAllFees();

                    int feeId = getInt(sc, "Enter Fee ID: ");
                    double payAmt = getDouble(sc, "Amount: ");

                    // ✅ CHANGED HERE
                    String[] modes = { "CASH", "UPI" };

                    for (int i = 0; i < modes.length; i++) {
                        System.out.println((i + 1) + ". " + modes[i]);
                    }

                    int mode = getInt(sc, "Choose mode: ");
                    if (mode < 1 || mode > 2) {   // ✅ CHANGED
                        System.out.println("Invalid.");
                        break;
                    }

                    System.out.print("Reference: ");
                    String ref = sc.next();

                    payments.makePayment(feeId, payAmt, modes[mode - 1], ref);
                    break;

                case 14:
                    System.out.print("Resident ID: ");
                    String view = sc.next();
                    payments.displayFeesForResident(view);
                    payments.displayPaymentHistory(view);
                    break;

                case 15:
                    int days = getInt(sc, "Days ahead: ");
                    payments.generatePaymentReminders(days);
                    break;

                case 16:
                    payments.generateFinancialReport();
                    break;

                case 7:
                    System.out.println("Exiting...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}