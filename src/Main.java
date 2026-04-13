import hostel.HostelManager;
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

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HostelManager hm = new HostelManager();

        System.out.println("=== Smart Hostel Management System ===");

        while (true) {
            System.out.println("\n1. Add Resident");
            System.out.println("2. Add Room");
            System.out.println("3. Allocate Room");
            System.out.println("4. Vacate Room");
            System.out.println("5. Show Residents");
            System.out.println("6. Show Rooms");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {

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

                    System.out.print("Enter Capacity: ");
                    int cap = sc.nextInt();

                    hm.addRoom(roomNo, cap);

                    System.out.println("Room added!");
                    break;

                case 3:
                    System.out.print("Enter Resident ID: ");
                    String rid = sc.next();

                    System.out.print("Enter Room Number: ");
                    int rno = sc.nextInt();

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

                case 7:
                    System.out.println("Exiting...");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}