import java.util.ArrayList;
import java.util.List;

public class SecurityManager {

    private VisitorLog visitorLog;
    private List<String> incidentReports;
    private NotificationService notifService;

    public SecurityManager(NotificationService notifService) {
        this.visitorLog = new VisitorLog();
        this.incidentReports = new ArrayList<>();
        this.notifService = notifService;
    }

    // Overloaded logVisitor — without ID proof
    public void logVisitor(String name, String contact, String purpose, String residentId) {
        Visitor v = new Visitor(name, contact, purpose, residentId);
        visitorLog.addVisitor(v);
    }

    // Overloaded logVisitor — with ID proof (vararg overloading: extra details)
    public void logVisitor(String name, String contact, String purpose,
                           String residentId, String... extraDetails) {
        Visitor v = new Visitor(name, contact, purpose, residentId);
        visitorLog.addVisitor(v);
        System.out.println("[Security] Extra details: " + String.join(", ", extraDetails));
    }

    public void recordVisitorExit(String visitorId) {
        visitorLog.recordExit(visitorId);
    }

    // Reports an incident and optionally raises an alert
    public void reportIncident(String description) {
        incidentReports.add(description);
        notifService.sendAlert("SECURITY", description);
        System.out.println("[Security] Incident reported: " + description);
    }

    // Throws UnauthorisedAccessException when invalid badge is used
    public void checkAccess(String userId, String requiredRole, String userRole)
            throws UnauthorisedAccessException {
        if (!userRole.equalsIgnoreCase(requiredRole) && !userRole.equalsIgnoreCase("ADMIN")) {
            throw new UnauthorisedAccessException(
                    "User " + userId + " does not have " + requiredRole + " access.");
        }
        System.out.println("[Security] Access granted to: " + userId);
    }

    public void displayVisitorLog() { visitorLog.displayAll(); }

    public void displayIncidents() {
        System.out.println("\n===== Incident Reports =====");
        if (incidentReports.isEmpty()) System.out.println("No incidents.");
        else for (int i = 0; i < incidentReports.size(); i++) {
            System.out.println((i + 1) + ". " + incidentReports.get(i));
        }
        System.out.println("============================\n");
    }
}