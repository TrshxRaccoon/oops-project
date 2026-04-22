import hostel.HostelManager;
import java.time.LocalDate;
import java.util.Scanner;
import services.MessService;
import services.PaymentServices;
import users.*;

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

        Authentication currentUser = null;
        Admin admin = new Admin("admin1", "Warden");

        System.out.println("=== Smart Hostel Management System ===");

        while (true) {

            boolean isLoggedIn = currentUser != null && currentUser.isLoggedIn();
            boolean isAdmin = isLoggedIn && currentUser.getRole().equals("ADMIN");

            System.out.println("\n--- Auth ---");
            System.out.println("0. Login as Admin");
            System.out.println("17. Login as Resident");
            System.out.println("18. Logout");

            if (!isLoggedIn) {
                System.out.println("\n(Login required)");
                System.out.println("7. Exit");
            } else if (isAdmin) {
                System.out.println("\n--- Admin ---");
                System.out.println("1. Add Resident");
                System.out.println("2. Add Room");
                System.out.println("3. Allocate Room");
                System.out.println("4. Vacate Room");
                System.out.println("5. Show Residents");
                System.out.println("6. Show Rooms");
                System.out.println("12. Add Fee");
                System.out.println("15. Payment Reminders");
                System.out.println("16. Financial Report");
                System.out.println("13. Make Payment");
                System.out.println("14. View Fees");
                System.out.println("8. View Menu");
                System.out.println("9. Subscribe Mess");
                System.out.println("10. Feedback");
                System.out.println("11. View Feedback");
                System.out.println("7. Exit");
            } else {
                System.out.println("\n--- Resident ---");
                System.out.println("13. Make Payment");
                System.out.println("14. View Fees");
                System.out.println("8. View Menu");
                System.out.println("9. Subscribe Mess");
                System.out.println("10. Feedback");
                System.out.println("11. View Feedback");
                System.out.println("7. Exit");
            }

            int choice = getInt(sc, "\nEnter choice: ");

            if ((choice >= 1 && choice <= 6) || choice == 12 || choice == 15 || choice == 16) {
                if (currentUser == null || !currentUser.getRole().equals("ADMIN")) {
                    System.out.println("Admin access required.");
                    continue;
                }
            }

            switch (choice) {

                case 0:
                    System.out.print("Admin ID: ");
                    String aid = sc.next();
                    if (admin.login(aid)) currentUser = admin;
                    break;

                case 17:
                    System.out.print("Resident ID: ");
                    String ridLogin = sc.next();
                    Resident rLogin = new Resident(ridLogin, "User");
                    if (rLogin.login(ridLogin)) currentUser = rLogin;
                    break;

                case 18:
                    if (currentUser != null) {
                        currentUser.logout();
                        currentUser = null;
                    }
                    break;

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
                    String[] feeTypes = {"HOSTEL", "TUITION", "MESS"};

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
                    String[] modes = {"CASH", "UPI"};

                    for (int i = 0; i < modes.length; i++) {
                        System.out.println((i + 1) + ". " + modes[i]);
                    }

                    int mode = getInt(sc, "Choose mode: ");
                    if (mode < 1 || mode > 2) {   // ✅ CHANGED
                        System.out.println("Invalid.");
                        break;
                    }

                    payments.makePayment(feeId, payAmt, modes[mode - 1]);
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