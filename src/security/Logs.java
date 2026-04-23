package security;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logs {
    protected String dateAndTime;
    protected String type; // Entry or exit or unauth
    protected Visitor v;
    protected boolean isInside;

    public Logs(Visitor v, String type) {
        this.v = v;
        this.dateAndTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        this.type = type;

        if (!v.isAuthorised()) {
            this.type = "unauth";
            System.out.println("Unauthorised Entry");
            throw new UnauthorisedAccessException("Unauthorized access detected");
        }
    }

    public Logs(Visitor v, String dateAndTime, String type) {
        this.dateAndTime = dateAndTime;
        this.v = v;
        this.type = type;

        if (!v.isAuthorised()) {
            this.type = "unauth";
            System.out.println("Unauthorised Entry");
            throw new UnauthorisedAccessException("Unauthorized access detected");
        }
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public String getType() {
        return type;
    }

    public Visitor getVisitor() {
        return v;
    }

    public boolean isInside() {
        return isInside;
    }

    public boolean getIsInside() {
        return isInside;
    }

    public String getVisitorID() {
        return v != null ? v.getID() : null;
    }

    public String getVisitorName() {
        return v != null ? v.getName() : null;
    }

    public boolean isUnauthorised() {
        return "unauth".equals(type);
    }

    public boolean isEntry() {
        return "Entry".equalsIgnoreCase(type);
    }

    public boolean isExit() {
        return "Exit".equalsIgnoreCase(type);
    }
}