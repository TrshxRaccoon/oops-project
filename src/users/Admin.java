package users;

public class Admin extends User implements Authentication {

    private boolean loggedIn = false;

    public Admin(String id, String name) {
        super(id, name);
    }

    @Override
    public void login(String id) {
        if (this.id.equals(id)) {
            loggedIn = true;
            System.out.println("Admin logged in successfully");
        } else {
            System.out.println("Invalid credentials");
        }
    }

    @Override
    public void logout() {
        loggedIn = false;
        System.out.println("Admin logged out");
    }

    @Override
    public void display() {
        System.out.println("Admin ID: " + id + " | Name: " + name);
    }
}