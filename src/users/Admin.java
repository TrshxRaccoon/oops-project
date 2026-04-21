package users;

public class Admin extends User implements Authentication {

    private boolean loggedIn = false;

    public Admin(String id, String name) {
        super(id, name);
    }

    @Override
    public boolean login(String id) {
        if (this.id.equals(id)) {
            loggedIn = true;
            System.out.println("Admin logged in successfully");
            return true;
        } else {
            System.out.println("Invalid credentials");
            return false;
        }
    }

    @Override
    public void logout() {
        loggedIn = false;
        System.out.println("Admin logged out");
    }

    @Override
    public boolean isLoggedIn() {
        return loggedIn;
    }

    @Override
    public String getRole() {
        return "ADMIN";
    }

    @Override
    public void display() {
        if (loggedIn) {
            System.out.println("Admin ID: " + id + " | Name: " + name);
        } else {
            System.out.println("Please login first");
        }
    }
}