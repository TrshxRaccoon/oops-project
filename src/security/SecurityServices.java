package security;
import java.util.ArrayList;

public class SecurityServices 
{
    private ArrayList<Logs> logs;//holds all security logs
    private ArrayList<Visitor> visitors;//holds all visitors

    public SecurityServices() 
    {
        logs = new ArrayList<>();
        visitors = new ArrayList<>();
    }

    public void recordLog(Visitor v, String type) 
    {
        try 
        {
            logs.add(new Logs(v, type));
        } 
        catch (UnauthorisedAccessException e) 
        {
            System.out.println("Unauthorised entry and name recorded");
        }
    }

    public void recordLog(Visitor v, String dateAndTime, String type) 
    {
        try 
        {
            logs.add(new Logs(v, dateAndTime, type));
        } 
        catch (UnauthorisedAccessException e) 
        {
            System.out.println("Unauthorised entry and name recorded");
        }
    }

    public void getLastFewLogs(int n)
    {
        for (int i = 0; i < n; i++) 
        {
            if (logs.size() - 1 - i < 0) 
            {
                System.out.println("End of logs reached");
                break;
            }

            Logs l = logs.get(logs.size() - 1 - i);
            System.out.println("Entry by " + l.getVisitorName() + " | VisitorId : " + l.getVisitorID());
        }
    }

    public void getVisitorInfo(int visitorId)
    {
        if (visitorId < 0 || visitorId >= visitors.size())
        {
            System.out.println("Invalid Visitor ID");
            return;
        }

        Visitor v = visitors.get(visitorId);
        System.out.println("Name of Visitor : " + v.getName() + " | Visitor Id : " + visitorId + " | Visiting : " + v.getVisiteeId() + " | Is Authorised : " + v.isAuthorised());
    }

    public void addVisitor(String name, String visiteeId, boolean g, boolean auth)
    {
        Visitor v = new Visitor(visitors.size(), name, visiteeId, g, auth);
        visitors.add(v);
        System.out.println("Your VisitorId is " + (visitors.size() - 1) + " | please don't forget it");
    }

    public void addVisitor(String name, String visiteeId, boolean g)
    {
        Visitor v = new Visitor(visitors.size(), name, visiteeId, g);
        visitors.add(v);
        System.out.println("Your VisitorId is " + (visitors.size() - 1) + " | please don't forget it");
    }
}