package users;

public class Resident extends User implements Authentication 
{
    private boolean loggedIn = false;

    public static class Guardian 
    {
        private final String name;
        private final String phone;

        public Guardian(String name, String phone) 
        {
            this.name = name;
            this.phone = phone;
        }

        public void display() 
        {
            System.out.println("Guardian: " + name + " | Phone: " + phone);
        }
    }

    private Guardian guardian;

    public Resident(String id, String name) 
    {
        super(id, name);
    }

    public Resident(String id, String name, Guardian guardian) 
    {
        super(id, name);
        this.guardian = guardian;
    }

    public void setGuardian(Guardian guardian) 
    {
        this.guardian = guardian;
    }

    public void display() 
    {
        System.out.println("Resident ID: " + id + " | Name: " + name);
        if (guardian != null)
        {
            guardian.display();
        }
    }

    public boolean login(String id)
    {
        if (this.id.equals(id)) 
        {
            loggedIn = true;
            System.out.println("Resident logged in successfully");
            return true;
        }
        System.out.println("Invalid Resident ID");
        return false;
    }

    public void logout() 
    {
        loggedIn = false;
        System.out.println("Resident logged out");
    }

    public boolean isLoggedIn()
    {
        return loggedIn;
    }

    public String getRole()
    {
        return "RESIDENT";
    }
}