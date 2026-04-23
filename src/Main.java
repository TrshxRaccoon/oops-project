import hostel.HostelManager;
import java.time.LocalDate;
import java.util.Scanner;
import security.SecurityServices;
import security.Visitor;
import services.MessService;
import services.PaymentServices;
import users.Admin;
import users.Authentication;
import users.Resident;

public class Main 
{
    public static int getInt(Scanner sc, String msg)
    {
        while (true) 
        {
            try
            {
                System.out.print(msg);
                int v = sc.nextInt();
                sc.nextLine();
                return v;
            } 
            catch (Exception e)
            {
                System.out.println("Invalid number");
                sc.nextLine();
            }
        }
    }

    public static double getDouble(Scanner sc, String msg)
    {
        while (true)
        {
            try
            {
                System.out.print(msg);
                double v = sc.nextDouble();
                sc.nextLine();
                return v;
            }
            catch (Exception e) 
            {
                System.out.println("Invalid number");
                sc.nextLine();
            }
        }
    }

    public static void pause(Scanner sc) 
    {
        System.out.println("\nPress Enter to continue:");
        sc.nextLine();
    }

    public static void main(String[] args)
    {

        Scanner sc = new Scanner(System.in);
        HostelManager hm = new HostelManager();
        MessService mess = new MessService(hm);
        PaymentServices payments = new PaymentServices(hm);
        SecurityServices security = new SecurityServices();
        Authentication currentUser = null;
        Admin admin = new Admin("admin1", "Warden");
        String message = "";

        while (true)
        {

            if (!message.isEmpty())
            {
                System.out.println(message);
                message = "";
            }

            if (currentUser == null)
            {
                System.out.println("LOGIN:");
                System.out.println("1. Admin Login");
                System.out.println("2. Resident Login");
                System.out.println("0. Exit");

                int ch = getInt(sc, "Choice: ");

                switch (ch)
                {
                    case 1:
                        System.out.print("Admin ID: ");
                        if (admin.login(sc.next())) 
                        {
                            currentUser = admin;
                        }
                        else
                        {
                            message = "Invalid credentials";
                        }
                        break;

                    case 2:
                        System.out.print("Resident ID: ");
                        String id = sc.next();
                        Resident existing = hm.getResident(id);
                        if (existing == null)
                        {
                            message = "Invalid Resident ID";
                        }
                        else if (existing.login(id))
                        {
                            currentUser = existing;
                        }
                        else
                        {
                            message = "Login failed";
                        }
                        break;

                    case 0:
                        return;
                }
                continue;
            }

            boolean isAdmin = currentUser.getRole().equals("ADMIN");
            System.out.println("MAIN MENU:");

            if (isAdmin)
            {
                System.out.println("1. Residents");
                System.out.println("2. Rooms");
                System.out.println("3. Payments");
                System.out.println("4. Mess");
                System.out.println("5. Visitors");
            } 
            else
            {
                System.out.println("3. Payments");
                System.out.println("4. Mess");
            }

            System.out.println("6. Logout");
            System.out.println("0. Exit");

            int choice = getInt(sc, "Choice: ");
            switch (choice)
            {
                case 1:
                    if (isAdmin) residentsMenu(sc, hm);
                    break;

                case 2:
                    if (isAdmin) roomsMenu(sc, hm);
                    break;

                case 3:
                    paymentsMenu(sc, payments, currentUser);
                    break;

                case 4:
                    messMenu(sc, mess, currentUser);
                    break;

                case 5:
                    if (isAdmin) visitorsMenu(sc, security);
                    break;

                case 6:
                    currentUser.logout();
                    currentUser = null;
                    break;

                case 0:
                    return;
            }
        }
    }

    private static void residentsMenu(Scanner sc, HostelManager hm)
    {
        while (true)
        {
            System.out.println("RESIDENTS:");
            System.out.println("1. Add Resident");
            System.out.println("2. Show Residents");
            System.out.println("3. Back");

            int c = getInt(sc, "Choice: ");

            switch (c)
            {
                case 1:
                    System.out.print("ID: ");
                    String id = sc.next();
                    System.out.print("Name: ");
                    String name = sc.next();
                    hm.addResident(new Resident(id, name));
                    break;

                case 2:
                    hm.showResidents();
                    pause(sc);
                    break;

                case 3:
                    return;
            }
        }
    }

    private static void roomsMenu(Scanner sc, HostelManager hm)
    {
        while (true)
        {
            System.out.println("ROOMS:");
            System.out.println("1. Add Room");
            System.out.println("2. Allocate");
            System.out.println("3. Vacate");
            System.out.println("4. Show Rooms");
            System.out.println("5. Back");

            int c = getInt(sc, "Choice: ");

            switch (c)
            {
                case 1:
                    int roomNo = getInt(sc, "Room No: ");
                    int capacity = getInt(sc, "Capacity: ");
                    if (capacity<=0) {
                        System.out.println("Please enter a capacity greater than 0");
                        break;
                    }
                    hm.addRoom(roomNo, capacity);
                    break;

                case 2:
                    System.out.print("Resident ID: ");
                    hm.allocateRoom(sc.next(), getInt(sc, "Room No: "));
                    break;

                case 3:
                    System.out.print("Resident ID: ");
                    hm.vacateRoom(sc.next());
                    break;

                case 4:
                    hm.showRooms();
                    pause(sc);
                    break;

                case 5:
                    return;
            }
        }
    }

    private static void paymentsMenu(Scanner sc, PaymentServices p, Authentication currentUser)
    {
        while (true)
        {
            System.out.println("PAYMENTS:");
            System.out.println("1. Add Fee");
            System.out.println("2. Pay");
            System.out.println("3. View Fees");
            System.out.println("4. Report");
            System.out.println("5. Back");

            int c = getInt(sc, "Choice: ");

            switch (c)
            {
                case 1:
                    String id;
                    if (currentUser.getRole().equals("ADMIN"))
                    {
                        System.out.print("Resident ID: ");
                        id = sc.next();
                    } 
                    else
                    {
                        id = ((Resident) currentUser).getId();
                    }
                    double amt = getDouble(sc, "Amount: ");
                    int due = getInt(sc, "Due days: ");

                    System.out.println("Fee Types:");
                    System.out.println("1. HOSTEL");
                    System.out.println("2. TUITION");
                    System.out.println("3. MESS");

                    int typeChoice = getInt(sc, "Choose type: ");

                    String feeType;
                    switch (typeChoice)
                    {
                        case 1:
                            feeType = "HOSTEL"; 
                            break;

                        case 2: 
                            feeType = "TUITION"; 
                            break;
                        
                        case 3:
                            feeType = "MESS"; 
                            break;
                        
                        default:
                            System.out.println("Invalid type");
                            return;
                    }

                    p.addFee(id, feeType, amt, LocalDate.now().plusDays(due));
                    break;

                case 2:
                    String pid;
                    if (currentUser.getRole().equals("ADMIN"))
                    {
                        System.out.print("Resident ID: ");
                        pid = sc.next();
                    }
                    else
                    {
                        pid = ((Resident) currentUser).getId();
                    }
                    p.makePayment(pid, getDouble(sc, "Amount: "));
                    break;

                case 3:
                    String vid;
                    if (currentUser.getRole().equals("ADMIN"))
                    {
                        System.out.print("Resident ID: ");
                        vid = sc.next();
                    }
                    else
                    {
                        vid = ((Resident) currentUser).getId();
                    }
                    p.displayFeesForResident(vid);
                    pause(sc);
                    break;

                case 4:
                    p.generateFinancialReport();
                    pause(sc);
                    break;

                case 5:
                    return;
            }
        }
    }

    private static void messMenu(Scanner sc, MessService m, Authentication currentUser)
    {
        while (true)
        {
            System.out.println("MESS:");
            System.out.println("1. View Menu");
            System.out.println("2. Subscribe");
            System.out.println("3. Feedback");
            System.out.println("4. Back");

            int c = getInt(sc, "Choice: ");

            switch (c)
            {
                case 1:
                    m.displayFullWeeklyMenu();
                    pause(sc);
                    break;

                case 2:
                    String id;
                    if (currentUser.getRole().equals("ADMIN"))
                    {
                        System.out.print("Resident ID: ");
                        id = sc.next();
                    }
                    else
                    {
                        id = ((Resident) currentUser).getId();
                    }

                    int months = getInt(sc, "Months: ");
                    m.subscribe(id, LocalDate.now(), LocalDate.now().plusMonths(months));
                    break;

                case 3:
                    String rid;
                    if (currentUser.getRole().equals("ADMIN"))
                    {
                        System.out.print("Resident ID: ");
                        rid = sc.next();
                    }
                    else
                    {
                        rid = ((Resident) currentUser).getId();
                    }

                    sc.nextLine();
                    System.out.print("Meal: ");
                    String meal = sc.nextLine();
                    int rating = getInt(sc, "Rating: ");
                    System.out.print("Comment: ");
                    String com = sc.nextLine();
                    m.submitFeedback(rid, meal, rating, com);
                    break;

                case 4:
                    return;
            }
        }
    }

    private static void visitorsMenu(Scanner sc, SecurityServices s)
    {
        while (true)
        {
            System.out.println("VISITORS:");
            System.out.println("1. Add Visitor");
            System.out.println("2. Info");
            System.out.println("3. Log Entry/Exit");
            System.out.println("4. Logs");
            System.out.println("5. Back");

            int c = getInt(sc, "Choice: ");

            switch (c)
            {
                case 1:
                    System.out.print("Name: ");
                    String name = sc.next();
                    System.out.print("Visitee ID: ");
                    String vid = sc.next();
                    s.addVisitor(name, vid, false, true);
                    break;

                case 2:
                    s.getVisitorInfo(getInt(sc, "Visitor ID: "));
                    pause(sc);
                    break;

                case 3:
                    int id = getInt(sc, "Visitor ID: ");
                    System.out.print("Type: ");
                    String type = sc.next();
                    Visitor v = new Visitor(id, "Temp");
                    s.recordLog(v, type);
                    break;

                case 4:
                    s.getLastFewLogs(getInt(sc, "Count: "));
                    pause(sc);
                    break;

                case 5:
                    return;
            }
        }
    }
}