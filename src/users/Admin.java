package users;

public class Admin extends User implements Authentication
{

    private boolean loggedIn = false;
    public Admin(String id, String name) 
    {
        super(id, name);
    }

    public boolean login(String id) 
    {
        if (this.id.equals(id)) 
        {
            loggedIn = true;
            System.out.println("Admin logged in successfully");
            return true;
        } 
        else 
        {
            System.out.println("Invalid credentials");
            return false;
        }
    }

    public void logout() 
    {
        loggedIn = false;
        System.out.println("Admin logged out");
    }

    public boolean isLoggedIn() 
    {
        return loggedIn;
    }

    public String getRole() 
    {
        return "ADMIN";
    }

    public void display()
    {
        if (loggedIn) 
        {
            System.out.println("Admin ID: " + id + " | Name: " + name);
        } 
        else
        {
            System.out.println("Please login first");
        }
    }
}