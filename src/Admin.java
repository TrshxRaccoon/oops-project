/**
 * Represents an administrator in the hostel management system.
 * Extends User — part of Hierarchical Inheritance from User.
 */
public class Admin extends User {

    private String designation;    // e.g. "Hostel Warden", "Assistant Warden"
    private String officeContact;  // Office phone number

    // Overloaded Constructor 1: Basic
    public Admin(String userId, String name, String email, String password) {
        super(userId, name, email, password, "ADMIN");
        this.designation = "Warden";
        this.officeContact = "N/A";
    }

    // Overloaded Constructor 2: Full with designation and contact
    public Admin(String userId, String name, String email, String password,
                 String designation, String officeContact) {
        super(userId, name, email, password, "ADMIN");
        this.designation = designation;
        this.officeContact = officeContact;
    }

    @Override
    public void displayInfo() {
        System.out.println("========== Admin Details ==========");
        System.out.println("User ID        : " + userId);
        System.out.println("Name           : " + name);
        System.out.println("Email          : " + email);
        System.out.println("Designation    : " + designation);
        System.out.println("Office Contact : " + officeContact);
        System.out.println("===================================");
    }

    @Override
    public String getAccessLevel() {
        return "ADMIN: Full access — manage residents, rooms, fees, security, and reports.";
    }

    // Getters
    public String getDesignation()   { return designation; }
    public String getOfficeContact() { return officeContact; }

    // Setters
    public void setDesignation(String d)    { this.designation = d; }
    public void setOfficeContact(String c)  { this.officeContact = c; }
}