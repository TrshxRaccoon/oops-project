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
}