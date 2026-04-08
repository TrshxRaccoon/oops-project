/**
 * Abstract base class representing a generic user in the hostel management system.
 * Demonstrates: Abstract class, Overloaded constructors, Wrapper usage
 */
public abstract class User {

    protected String userId;    // Unique identifier for each user
    protected String name;      // Full name of the user
    protected String email;     // Email address
    protected String password;  // Stored password
    protected String role;      // Role: "ADMIN" or "RESIDENT"

    // Overloaded Constructor 1: without role (defaults to "USER")
    public User(String userId, String name, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = "USER";
    }

    // Overloaded Constructor 2: with role specified
    public User(String userId, String name, String email, String password, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Abstract method — every subclass must define how they display their info
    public abstract void displayInfo();

    // Abstract method — each role has different access
    public abstract String getAccessLevel();

    // Getters
    public String getUserId()  { return userId; }
    public String getName()    { return name; }
    public String getEmail()   { return email; }
    public String getRole()    { return role; }

    // Setters with wrapper usage (Integer.parseInt etc. used in subclasses)
    public void setName(String name)    { this.name = name; }
    public void setEmail(String email)  { this.email = email; }
    public void setPassword(String pwd) { this.password = pwd; }

    /**
     * Validates login credentials.
     * Uses String (Wrapper-like) methods for comparison.
     */
    public boolean validateLogin(String email, String password) {
        return this.email.equalsIgnoreCase(email) && this.password.equals(password);
    }

    // Overloaded toString — no parameters
    @Override
    public String toString() {
        return "User[ID=" + userId + ", Name=" + name + ", Role=" + role + "]";
    }

    // Overloaded toString — with flag to show email
    public String toString(boolean showEmail) {
        if (showEmail) {
            return "User[ID=" + userId + ", Name=" + name
                    + ", Email=" + email + ", Role=" + role + "]";
        }
        return toString();
    }
}