import finance.*;
import mess.*;
import notification.*;
import reports.*;
import resident.*;
import room.*;
import security.*;
import users.*;
import utils.*;
import utils.CustomExceptions.*;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Main entry point for the Smart Hostel Management System.
 * Demonstrates: All OOP concepts integrated.
 */
public class main {

    static Authentication auth = new Authentication();
    static ResidentManager residentManager = new ResidentManager();
    static RoomManager roomManager = new RoomManager();
    static NotificationService notifService = new NotificationService();
    static SecurityManager securityManager = new SecurityManager(notifService);
    static PaymentManager paymentManager = new PaymentManager(notifService);
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        seedData();
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  Smart Hostel Management System      ║");
        System.out.println("║  BITS Pilani Goa Campus              ║");
        System.out.println("╚══════════════════════════════════════╝");

        while (true) {
            if (!auth.isLoggedIn()) {
                showLoginMenu();
            } else {
                String role = auth.getCurrentUser().getRole();
                if (role.equals("ADMIN")) showAdminMenu();
                else showResidentMenu();
            }
        }
    }

    static void seedData() {
        // Add default admin
        Admin admin = new Admin("A001", "Dr. Sharma", "admin@bits.com", "admin123",
                "Chief Warden", "0832-123456");
        auth.register(admin);

        // Add some rooms
        roomManager.addRoom(new Room("A-101", Room.TYPE_SINGLE, 1, "1", "A"));
        roomManager.addRoom(new Room("A-102", Room.TYPE_DOUBLE, 2, "1", "A"));
        roomManager.addRoom(new Room("B-201", Room.TYPE_TRIPLE, 3, "2", "B"));

        // Add a resident
        GuardianDetails guardian = new GuardianDetails("Ramesh Kumar", "Father",
                "9876543210", "ramesh@gmail.com", "Mumbai");
        Resident r1 = new Resident("R001", "Arjun Mehta", "arjun@bits.com", "res123",
                "2023A7PS001G", "B.E. CS", "9123456789", "A-101", guardian);
        auth.register(r1);
        residentManager.addResident(r1);

        // Allocate room
        try {
            roomManager.allocateRoom(r1, "A-101");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Add a fee
        paymentManager.addFee(new Fee("R001", 25000.0,
                "Hostel Fee - Sem 1 2025", "2025-07-31"));
    }

    static void showLoginMenu() {
        System.out.println("\n--- LOGIN ---");
        System.out.println("1. Login\n2. Exit");
        System.out.print("Choice: ");
        String choice = scanner.nextLine().trim();
        if (choice.equals("1")) {
            System.out.print("Email: ");
            String email = scanner.nextLine().trim();
            System.out.print("Password: ");
            String pwd = scanner.nextLine().trim();
            auth.login(email, pwd);
        } else {
            System.out.println("Goodbye!");
            System.exit(0);
        }
    }

    static void showAdminMenu() {
        System.out.println("\n=== ADMIN MENU ===");
        System.out.println("1. View All Residents");
        System.out.println("2. View All Rooms");
        System.out.println("3. List Available Rooms");
        System.out.println("4. View Visitor Log");
        System.out.println("5. View All Fees");
        System.out.println("6. Send Broadcast Notification");
        System.out.println("7. Generate Financial Report");
        System.out.println("8. View Entry/Exit Logs");
        System.out.println("9. View All Notifications");
        System.out.println("10. Send Payment Reminders");
        System.out.println("0. Logout");
        System.out.print("Choice: ");
        String choice = scanner.nextLine().trim();

        try {
            switch (choice) {
                case "1": residentManager.listAllResidents(); break;
                case "2": roomManager.listAllRooms(); break;
                case "3": roomManager.listAvailableRooms(); break;
                case "4": securityManager.displayVisitorLog(); break;
                case "5": paymentManager.listAllFees(); break;
                case "6":
                    System.out.print("Message: ");
                    String msg = scanner.nextLine().trim();
                    notifService.broadcastNotification("Announcement", msg);
                    break;
                case "7":
                    FinancialReport fr = new FinancialReport(
                            auth.getCurrentUser().getName(),
                            paymentManager.getFees(),
                            paymentManager.getPayments());
                    fr.generateReport();
                    break;
                case "8": residentManager.displayEntryLogs(); break;
                case "9": notifService.listAllNotifications(); break;
                case "10": paymentManager.sendPaymentReminders(); break;
                case "0": auth.logout(); break;
                default: System.out.println("Invalid option.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void showResidentMenu() {
        User u = auth.getCurrentUser();
        System.out.println("\n=== RESIDENT MENU === [" + u.getName() + "]");
        System.out.println("1. View My Info");
        System.out.println("2. Check In");
        System.out.println("3. Check Out");
        System.out.println("4. View Mess Menu");
        System.out.println("5. View My Notifications");
        System.out.println("6. Log a Visitor");
        System.out.println("7. View My Fees");
        System.out.println("0. Logout");
        System.out.print("Choice: ");
        String choice = scanner.nextLine().trim();

        try {
            // Get resident object
            Resident res = null;
            for (users.Resident r : residentManager.getAllResidents()) {
                if (r.getUserId().equals(u.getUserId())) { res = r; break; }
            }

            switch (choice) {
                case "1":
                    if (res != null) res.displayInfo();
                    System.out.println(u.getAccessLevel());
                    break;
                case "2":
                    if (res != null) residentManager.recordEntry(res.getStudentId(), "Returning to hostel");
                    break;
                case "3":
                    if (res != null) residentManager.recordExit(res.getStudentId());
                    break;
                case "4":
                    MessMenu menu = new MessMenu("MENU-W1", "2025-07-07");
                    menu.setDayMenu("Monday", "Idli", "Dal Rice", "Samosa", "Roti Paneer");
                    menu.displayWeeklyMenu();
                    break;
                case "5":
                    notifService.showNotificationsForUser(u.getUserId());
                    break;
                case "6":
                    System.out.print("Visitor Name: ");
                    String vName = scanner.nextLine().trim();
                    System.out.print("Visitor Contact: ");
                    String vContact = scanner.nextLine().trim();
                    System.out.print("Purpose: ");
                    String purpose = scanner.nextLine().trim();
                    if (res != null)
                        securityManager.logVisitor(vName, vContact, purpose, res.getStudentId());
                    break;
                case "7":
                    System.out.println("\n--- Your Fees ---");
                    for (finance.Fee f : paymentManager.getFees()) {
                        if (f.getResidentId().equals(u.getUserId())) f.display();
                    }
                    break;
                case "0": auth.logout(); break;
                default: System.out.println("Invalid option.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}