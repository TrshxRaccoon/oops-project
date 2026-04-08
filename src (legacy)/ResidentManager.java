import java.util.ArrayList;
import java.util.List;

/**
 * Manages all resident-related operations.
 * Demonstrates:
 *   - Static Nested Class (ResidentStats)
 *   - Overloaded methods
 *   - Exception handling
 */
public class ResidentManager {

    private List<Resident> residents;       // All registered residents
    private List<EntryLog> entryLogs;       // All entry/exit logs

    // ─────────────────────────────────────────────────────────────
    // STATIC NESTED CLASS — provides aggregate stats without needing
    // an outer class instance
    // ─────────────────────────────────────────────────────────────
    public static class ResidentStats {
        private int totalResidents;        // Total number of residents
        private int checkedInCount;        // Currently inside hostel
        private int unassignedRooms;       // Residents without rooms

        public ResidentStats(int totalResidents, int checkedInCount, int unassignedRooms) {
            this.totalResidents = totalResidents;
            this.checkedInCount = checkedInCount;
            this.unassignedRooms = unassignedRooms;
        }

        public void display() {
            System.out.println("======= Resident Statistics =======");
            System.out.println("Total Residents   : " + totalResidents);
            System.out.println("Currently Inside  : " + checkedInCount);
            System.out.println("Unassigned Rooms  : " + unassignedRooms);
            System.out.println("===================================");
        }
    }
    // ─────────────────────────────────────────────────────────────

    public ResidentManager() {
        this.residents = new ArrayList<>();
        this.entryLogs = new ArrayList<>();
    }

    /** Adds a resident to the system */
    public void addResident(Resident resident) {
        residents.add(resident);
        System.out.println("[ResidentManager] Resident added: " + resident.getName());
    }

    /**
     * Overloaded findResident — by student ID string
     */
    public Resident findResident(String studentId) throws ResidentNotFoundException {
        for (Resident r : residents) {
            if (r.getStudentId().equals(studentId)) return r;
        }
        throw new ResidentNotFoundException("Resident with ID '" + studentId + "' not found.");
    }

    /**
     * Overloaded findResident — by index position (Integer wrapper)
     */
    public Resident findResident(Integer index) throws ResidentNotFoundException {
        if (index == null || index < 0 || index >= residents.size()) {
            throw new ResidentNotFoundException("Invalid resident index: " + index);
        }
        return residents.get(index);
    }

    /**
     * Records a check-in for the resident.
     * Overloaded recordEntry — without purpose
     */
    public void recordEntry(String studentId) throws ResidentNotFoundException {
        Resident r = findResident(studentId);
        r.setCheckedIn(true);
        EntryLog log = new EntryLog(r.getUserId(), r.getName(), true);
        entryLogs.add(log);
        System.out.println("[ResidentManager] Check-in recorded for: " + r.getName());
    }

    /**
     * Overloaded recordEntry — with purpose
     */
    public void recordEntry(String studentId, String purpose) throws ResidentNotFoundException {
        Resident r = findResident(studentId);
        r.setCheckedIn(true);
        EntryLog log = new EntryLog(r.getUserId(), r.getName(), true, purpose);
        entryLogs.add(log);
        System.out.println("[ResidentManager] Check-in recorded for: " + r.getName()
                + " | Purpose: " + purpose);
    }

    /** Records a check-out for the resident */
    public void recordExit(String studentId) throws ResidentNotFoundException {
        Resident r = findResident(studentId);
        r.setCheckedIn(false);
        EntryLog log = new EntryLog(r.getUserId(), r.getName(), false);
        entryLogs.add(log);
        System.out.println("[ResidentManager] Check-out recorded for: " + r.getName());
    }

    /** Removes a resident by student ID */
    public void removeResident(String studentId) throws ResidentNotFoundException {
        Resident r = findResident(studentId);
        residents.remove(r);
        System.out.println("[ResidentManager] Resident removed: " + r.getName());
    }

    /** Displays all residents */
    public void listAllResidents() {
        System.out.println("\n===== All Residents =====");
        if (residents.isEmpty()) {
            System.out.println("No residents registered.");
        } else {
            for (Resident r : residents) {
                r.displayInfo();
            }
        }
        System.out.println("=========================\n");
    }

    /** Displays all entry/exit logs */
    public void displayEntryLogs() {
        System.out.println("\n===== Entry/Exit Logs =====");
        if (entryLogs.isEmpty()) {
            System.out.println("No logs found.");
        } else {
            for (EntryLog log : entryLogs) {
                log.displayLog();
            }
        }
        System.out.println("===========================\n");
    }

    /**
     * Generates and returns a ResidentStats object (uses the static nested class).
     */
    public ResidentStats getStats() {
        int total = residents.size();
        int checkedIn = 0;
        int unassigned = 0;
        for (Resident r : residents) {
            if (r.isCheckedIn()) checkedIn++;
            if (r.getRoomNumber().equals("UNASSIGNED")) unassigned++;
        }
        return new ResidentStats(total, checkedIn, unassigned);
    }

    public List<Resident> getAllResidents() { return residents; }
    public List<EntryLog> getEntryLogs()   { return entryLogs; }
}