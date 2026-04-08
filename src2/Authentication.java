import java.util.ArrayList;
import java.util.List;

/**
 * Handles user registration, login, and session management.
 * Demonstrates: Overloaded methods, Wrapper types
 */
public class Authentication {

    private List<User> registeredUsers;   // All registered users
    private User currentUser;             // Currently logged-in user

    public Authentication() {
        this.registeredUsers = new ArrayList<>();
        this.currentUser = null;
    }

    /**
     * Registers a new user into the system.
     * Overloaded Method 1: register with User object
     */
    public boolean register(User user) {
        // Check duplicate email using String wrapper methods
        for (User u : registeredUsers) {
            if (u.getEmail().equalsIgnoreCase(user.getEmail())) {
                System.out.println("[Auth] Registration failed: Email already exists.");
                return false;
            }
        }
        registeredUsers.add(user);
        System.out.println("[Auth] User registered successfully: " + user.getName());
        return true;
    }

    /**
     * Overloaded Method 2: register with raw fields (creates a basic user check)
     * Used for quick validation before full object creation.
     */
    public boolean register(String email, String password, String role) {
        for (User u : registeredUsers) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                System.out.println("[Auth] Registration failed: Email already exists.");
                return false;
            }
        }
        // This variant is used for pre-validation; actual object is passed via register(User)
        System.out.println("[Auth] Pre-validation passed for: " + email);
        return true;
    }

    /**
     * Logs in a user by matching email and password.
     * Overloaded Method 3: login with both credentials
     */
    public User login(String email, String password) {
        for (User u : registeredUsers) {
            if (u.validateLogin(email, password)) {
                this.currentUser = u;
                System.out.println("[Auth] Login successful. Welcome, " + u.getName()
                        + " (" + u.getRole() + ")");
                return u;
            }
        }
        System.out.println("[Auth] Login failed: Invalid credentials.");
        return null;
    }

    /**
     * Overloaded Method 4: login with just email (used for account lookup)
     */
    public User login(String email) {
        for (User u : registeredUsers) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                System.out.println("[Auth] Account found for: " + email);
                return u;
            }
        }
        System.out.println("[Auth] No account found for: " + email);
        return null;
    }

    /** Logs out the current session */
    public void logout() {
        if (currentUser != null) {
            System.out.println("[Auth] " + currentUser.getName() + " logged out.");
            currentUser = null;
        } else {
            System.out.println("[Auth] No user is currently logged in.");
        }
    }

    /**
     * Role-based access check.
     * Uses Boolean wrapper for return and String wrapper for comparison.
     */
    public Boolean hasAccess(String requiredRole) {
        if (currentUser == null) return Boolean.FALSE;
        return Boolean.valueOf(currentUser.getRole().equalsIgnoreCase(requiredRole)
                || currentUser.getRole().equalsIgnoreCase("ADMIN"));
    }

    // Getters
    public User getCurrentUser()         { return currentUser; }
    public List<User> getAllUsers()       { return registeredUsers; }
    public boolean isLoggedIn()          { return currentUser != null; }

    /** Display all registered users — admin only action */
    public void listAllUsers() {
        System.out.println("\n===== Registered Users =====");
        // Using Integer wrapper for display index
        Integer index = 1;
        for (User u : registeredUsers) {
            System.out.println(index + ". " + u.toString(true));
            index++;
        }
        System.out.println("============================\n");
    }
}