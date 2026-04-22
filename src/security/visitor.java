package security;

import users.User;

public class visitor extends user
{
    protected String ID; //visitors ID, Assigned by SecurityServices
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
        if (g == true) this.isAuthorised = true; //guardians are always authorised
        else this.isAuthorised = auth ;
        if (visiteeId == null) auth == false;
    }

    public visitor(String ID , String name , String visiteeId , boolean g) //guardians are always authorised
    {
        this.ID = ID ;
        this.name = name ;
        this.visiteeId = visiteeId ;
        this.isGuardian = g ;
        this.isAuthorised = g;
    }

    public visitor(String ID , String name )
    {
        this.ID = ID ;
        this.name = name ;
        this.visiteeId = visiteeId ;
        this.isGuardian = g ;
        this.isAuthorised = g ;
    }


    //Getter Functions :( 
    //Boring as {blank} to write

    public boolean isAuthorised()
    {
        return this.isAuthorised;
    }

    public String getId() 
    {
        return ID;
    }
    
    public String getName() 
    {
        return name;
    }
    
    public String getVisiteId() 
    {
        return visiteId;
    }
}