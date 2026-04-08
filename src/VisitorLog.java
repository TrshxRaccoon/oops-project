import java.util.ArrayList;
import java.util.List;

public class VisitorLog {
    private List<Visitor> visitors = new ArrayList<>();

    public void addVisitor(Visitor v) {
        visitors.add(v);
        System.out.println("[VisitorLog] Visitor logged: " + v.getVisitorName());
    }

    public void recordExit(String visitorId) {
        for (Visitor v : visitors) {
            if (v.getVisitorId().equals(visitorId)) {
                v.recordExit();
                System.out.println("[VisitorLog] Exit recorded for: " + v.getVisitorName());
                return;
            }
        }
        System.out.println("[VisitorLog] Visitor not found: " + visitorId);
    }

    public void displayAll() {
        System.out.println("\n===== Visitor Log =====");
        if (visitors.isEmpty()) System.out.println("No visitor records.");
        else for (Visitor v : visitors) v.display();
        System.out.println("=======================\n");
    }

    public List<Visitor> getVisitors() { return visitors; }
}