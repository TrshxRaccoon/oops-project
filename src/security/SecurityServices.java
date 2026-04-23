package security;

public class SecurityService
{
    private ArrayList<log> logs;
    private int logCount;

    private ArrayList<visitor> visitors;
    private int visitorCount; //Their position in array also acts as their visiteeId

    public SecurityService ()
    {
        logs = new ArrayList<log>() ;
        logCount = 0 ;

        visitors = new ArrayList<visitor>() ;
        visitorCount = 0 ;
    }

    public void recordLog(visitor v ,String type) 
    {
        try
        {
            logs[logCount] = new log(v , type) ;
            logCount ++;
        }
        catch(UnauthorisedAccessException e)
        {
            System.out.println("Unauthorised entry,Name recorded") ;
        }
    }

    public void recordLog(visitor v,String dateAndTime ,String type)
    {
        try
        {
            logs[logCount] = new log(v , dateAndTime , type) ;
            logCount ++;
        }
        catch(UnauthorisedAccessException e)
        {
            System.out.println("Unauthorised entry,Name recorded") ;
        }
    }

    public void getLastFewLogs(int n)
    {
        for (int i = 0 ; i < n ; i++)
        {
            if (logCount - i < 0 ) 
            {
                System.out.println("End of logs reached,Could");
                break;
            }

            log l = logs.get(logCount - i);
            System.out.println("Entry by " + l.getVisitorName + "\nVisitorId : " + l.getVisitorID + "Visitng : " + l.getVisiteId);
        }
    }

    public void getVisitorInfo(int vistorId)
    {
        visitor v = visitors.get(visitorId);
        System.out.println("Name of Visitor : " + v.getName + "\nVisitor Id : " + visitorId + "\nVisitng : " + v.getVisiteId + "\nis authorised" + v.isAuthorised);
    }

    public void addVisitor(String Name , String visiteeId , boolean g , boolean auth)
    {
        visitors(visitorCount) = new visitor(visitorCount , Name , visiteeId , g , auth);
        System.out.println("Your VisitorId is " + visitorCount + "\nPlease Don't forget it");
    }

    public void addVisitor(String Name , String visiteeId , boolean g )
    {
        visitors(visitorCount) = new visitor(visitorCount , Name , visiteeId , g );
        System.out.println("Your VisitorId is " + visitorCount + "\nPlease Don't forget it");
    }
}