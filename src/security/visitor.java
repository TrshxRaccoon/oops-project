package security;

import users.User;

public class visitor extends user
{
    protected String ID; //visitors ID
    protected String name; 
    protected String visiteeId; //Id of resident being visited
    protected boolean isGuardian;
    protected boolean isAuthorised;

    public visitor(String ID , String name , String visiteeId , boolean g , boolean auth)
    {
        this.ID = ID;
        this.name = name;
        this.visiteeId = visiteeId;
        this.isGuardian = g ;
        this.isAuthorised = auth;
    }

    public visitor(String ID , String name , String visiteeId , boolean g)
    {
        this.ID = ID ;
        this.name = name ;
        this.visiteeId = visiteeId ;
        this.isGuardian = g ;
        this.isAuthorised = g;
    }
}