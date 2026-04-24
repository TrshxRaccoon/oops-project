package security;
import users.User;

public class Visitor extends User 
{
    protected int ID;//unique ID for each visitor
    protected String visiteeId;//ID of the resident being visited
    protected boolean isGuardian;//indicates if the visitor is a guardian of the resident
    protected boolean isAuthorised;//indicates if the visitor is authorised to enter the hostel

    public Visitor(int ID, String name, String visiteeId, boolean g, boolean auth) 
    {
        super(String.valueOf(ID), name);
        this.ID = ID;
        this.visiteeId = visiteeId;
        this.isGuardian = g;
        this.isAuthorised = g || auth;

        if (visiteeId == null) 
        {
            this.isAuthorised = false;
        }
    }

    public Visitor(int ID, String name, String visiteeId, boolean g) 
    {
        super(String.valueOf(ID), name);
        this.ID = ID;
        this.visiteeId = visiteeId;
        this.isGuardian = g;
        this.isAuthorised = g;
    }

    public Visitor(int ID, String name) 
    {
        super(String.valueOf(ID), name);
        this.ID = ID;
        this.visiteeId = null;
        this.isGuardian = false;
        this.isAuthorised = false;
    }

    public boolean isAuthorised() 
    {
        return this.isAuthorised;
    }

    public String getId() 
    {
        return super.getId();
    }

    public String getName() 
    {
        return name;
    }

    public String getVisiteeId() 
    {
        return visiteeId;
    }

    public void display() 
    {
        System.out.println("Visitor ID: " + ID + " | Name: " + name + " | Visitee: " + visiteeId + " | Authorised: " + isAuthorised);
    }
}