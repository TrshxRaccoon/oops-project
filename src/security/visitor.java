package security;

import users.User;

public class Visitor extends User {

    protected int ID; // visitor's numeric ID
    protected String visiteeId; // resident being visited
    protected boolean isGuardian;
    protected boolean isAuthorised;

    // Full constructor
    public Visitor(int ID, String name, String visiteeId, boolean g, boolean auth) {
        super(String.valueOf(ID), name);
        this.ID = ID;
        this.visiteeId = visiteeId;
        this.isGuardian = g;
        this.isAuthorised = g || auth;

        if (visiteeId == null) {
            this.isAuthorised = false;
        }
    }

    // Guardian constructor
    public Visitor(int ID, String name, String visiteeId, boolean g) {
        super(String.valueOf(ID), name);
        this.ID = ID;
        this.visiteeId = visiteeId;
        this.isGuardian = g;
        this.isAuthorised = g; // guardians auto-authorised
    }

    // Basic constructor
    public Visitor(int ID, String name) {
        super(String.valueOf(ID), name);
        this.ID = ID;
        this.visiteeId = null;
        this.isGuardian = false;
        this.isAuthorised = false;
    }

    // Getter methods

    public boolean isAuthorised() {
        return this.isAuthorised;
    }

    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public String getName() {
        return name;
    }

    public String getVisiteeId() {
        return visiteeId;
    }

    // Required abstract method implementation
    @Override
    public void display() {
        System.out.println("Visitor ID: " + ID +
                           " | Name: " + name +
                           " | Visitee: " + visiteeId +
                           " | Authorised: " + isAuthorised);
    }
}