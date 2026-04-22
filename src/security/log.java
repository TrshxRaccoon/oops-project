package security ;

public static class logs
{
    protected String dateAndTime;
    protected String type; // Entry or exit or unauth
    protected visitor v;
    protected boolean isInside;

    public log(visitor v , String type) 
    {
        this.v = v;
        this.dateAndTime = now.format(DateTimeFormatter.ISO_DATE_TIME).toString();
        this.type = type;
        if (! v.isAuthorised ) 
        {
            this.type = "unauth" ;
            System.out.println ("Unauthorised Entry")
            throw new UnauthorisedAccessException("Unauthorized access detected") ;
        }
    }
    public log(visitor v ,String dateAndTime ,String type) 
    {
        this.dateAndTime = dateAndTime ;
        this.v = v ;
        this.type = type ;
        if (! v.isAuthorised ) 
        {
            this.type = "unauth" ;
            System.out.println ("Unauthorised Entry")
            throw new UnauthorisedAccessException("Unauthorized access detected") ;
        }
    }

    //I hate writing getter
    
    public String getDateAndTime() {
        return dateAndTime;
    }
    
    public String getType() {
        return type;
    }
    
    public visitor getVisitor() {
        return v;
    }
    
    public boolean isInside() {
        return isInside;
    }
    
    public boolean getIsInside() 
    {
        return isInside;
    }

    
    public String getVisitorID() 
    {
        return v != null ? v.getID() : null;
    }
    
   
    public String getVisitorName() 
    {
        return v != null ? v.getName() : null;
    }
    
    
    public boolean isUnauthorised() 
    {
        return "unauth".equals(type);
    }
    
    
    public boolean isEntry() 
    {
        return "Entry".equalsIgnoreCase(type);
    }
    
    // Check if the log represents an exit
    public boolean isExit() 
    {
        return "Exit".equalsIgnoreCase(type);
    }
}