package users;

public class Resident extends User implements Authentication 
{

    private String course;
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

    public Resident(String id, String name, String course, Guardian guardian) 
    {
        super(id, name);
        this.course = course;
        this.guardian = guardian;
    }

    public void setCourse(String course) 
    {
        this.course = course;
    }

    public void setGuardian(Guardian guardian) 
    {
        this.guardian = guardian;
    }

    @Override
    public void display() 
    {
        System.out.println("Resident ID: " + id + " | Name: " + name + " | Course: " + course);
        if (guardian != null)
        {
            guardian.display();
        }
    }

    @Override
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

    @Override
    public void logout() 
    {
        loggedIn = false;
        System.out.println("Resident logged out");
    }

    @Override
    public boolean isLoggedIn()
    {
        return loggedIn;
    }

    @Override
    public String getRole()
    {
        return "RESIDENT";
    }
}