/**
 * Represents a student resident of the hostel.
 * Extends User — demonstrates Hierarchical Inheritance (both Resident and Admin extend User).
 */
public class Resident extends User {

    private String studentId;       // BITS student ID e.g. "2022A7PS0001G"
    private String course;          // Course enrolled e.g. "B.E. Computer Science"
    private String contactNumber;   // Personal contact number
    private String roomNumber;      // Allocated room number
    private GuardianDetails guardian; // Nested-class object for guardian info
    private boolean isCheckedIn;    // Whether the student is currently in hostel

    // Overloaded Constructor 1: Basic — no room assigned yet
    public Resident(String userId, String name, String email, String password,
                    String studentId, String course, String contactNumber) {
        super(userId, name, email, password, "RESIDENT");
        this.studentId = studentId;
        this.course = course;
        this.contactNumber = contactNumber;
        this.roomNumber = "UNASSIGNED";
        this.isCheckedIn = false;
    }

    // Overloaded Constructor 2: Full — with room and guardian
    public Resident(String userId, String name, String email, String password,
                    String studentId, String course, String contactNumber,
                    String roomNumber, GuardianDetails guardian) {
        super(userId, name, email, password, "RESIDENT");
        this.studentId = studentId;
        this.course = course;
        this.contactNumber = contactNumber;
        this.roomNumber = roomNumber;
        this.guardian = guardian;
        this.isCheckedIn = false;
    }

    @Override
    public void displayInfo() {
        System.out.println("========== Resident Details ==========");
        System.out.println("User ID      : " + userId);
        System.out.println("Name         : " + name);
        System.out.println("Student ID   : " + studentId);
        System.out.println("Course       : " + course);
        System.out.println("Contact      : " + contactNumber);
        System.out.println("Room         : " + roomNumber);
        System.out.println("Checked In   : " + (isCheckedIn ? "Yes" : "No"));
        if (guardian != null) {
            System.out.println("Guardian     : " + guardian.getGuardianName()
                    + " | " + guardian.getGuardianContact());
        }
        System.out.println("======================================");
    }

    @Override
    public String getAccessLevel() {
        return "RESIDENT: Can view room info, mess menu, fee status, and notifications.";
    }

    // Getters
    public String getStudentId()      { return studentId; }
    public String getCourse()         { return course; }
    public String getContactNumber()  { return contactNumber; }
    public String getRoomNumber()     { return roomNumber; }
    public GuardianDetails getGuardian() { return guardian; }
    public boolean isCheckedIn()      { return isCheckedIn; }

    // Setters
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public void setGuardian(GuardianDetails g)   { this.guardian = g; }
    public void setCheckedIn(boolean checkedIn)  { this.isCheckedIn = checkedIn; }
    public void setContactNumber(String c)       { this.contactNumber = c; }
}