/**
 * Represents guardian/emergency contact details for a resident.
 * This is used as a Static Nested Class concept (instantiated standalone but logically nested to Resident).
 * Demonstrates: Nested class (non-static, used inside Resident context)
 */
public class GuardianDetails {

    private String guardianName;      // Name of the guardian
    private String relationship;      // Relationship: Father, Mother, etc.
    private String guardianContact;   // Phone number of guardian
    private String guardianEmail;     // Email of guardian
    private String guardianAddress;   // Guardian's home address

    // Constructor 1: Basic — name and contact only
    public GuardianDetails(String guardianName, String guardianContact) {
        this.guardianName = guardianName;
        this.guardianContact = guardianContact;
        this.relationship = "Guardian";
        this.guardianEmail = "N/A";
        this.guardianAddress = "N/A";
    }

    // Constructor 2: Full details
    public GuardianDetails(String guardianName, String relationship,
                           String guardianContact, String guardianEmail,
                           String guardianAddress) {
        this.guardianName = guardianName;
        this.relationship = relationship;
        this.guardianContact = guardianContact;
        this.guardianEmail = guardianEmail;
        this.guardianAddress = guardianAddress;
    }

    // Getters
    public String getGuardianName()    { return guardianName; }
    public String getRelationship()    { return relationship; }
    public String getGuardianContact() { return guardianContact; }
    public String getGuardianEmail()   { return guardianEmail; }
    public String getGuardianAddress() { return guardianAddress; }

    // Setters
    public void setGuardianEmail(String e)   { this.guardianEmail = e; }
    public void setGuardianAddress(String a) { this.guardianAddress = a; }

    public void displayGuardianInfo() {
        System.out.println("  Guardian: " + guardianName + " (" + relationship + ")");
        System.out.println("  Contact : " + guardianContact);
        System.out.println("  Email   : " + guardianEmail);
        System.out.println("  Address : " + guardianAddress);
    }

    @Override
    public String toString() {
        return "Guardian[" + guardianName + " | " + relationship
                + " | " + guardianContact + "]";
    }
}