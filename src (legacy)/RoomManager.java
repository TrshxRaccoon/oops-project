import java.util.ArrayList;
import java.util.List;

/**
 * Manages all room-related operations: allocation, vacating, and change requests.
 * Demonstrates: Exception handling, Overloaded methods
 */
public class RoomManager {

    private List<Room> rooms;                        // All rooms in hostel
    private List<RoomChangeRequest> changeRequests;  // All room change requests

    public RoomManager() {
        this.rooms = new ArrayList<>();
        this.changeRequests = new ArrayList<>();
    }

    /** Adds a new room to the system */
    public void addRoom(Room room) {
        rooms.add(room);
        System.out.println("[RoomManager] Room added: " + room.getRoomNumber());
    }

    /**
     * Finds a room by room number.
     * Overloaded findRoom — by String
     * Throws RoomNotFoundException if not found.
     */
    public Room findRoom(String roomNumber) throws RoomNotFoundException {
        for (Room r : rooms) {
            if (r.getRoomNumber().equalsIgnoreCase(roomNumber)) return r;
        }
        throw new RoomNotFoundException("Room '" + roomNumber + "' not found.");
    }

    /**
     * Overloaded findRoom — by index (Integer wrapper)
     */
    public Room findRoom(Integer index) throws RoomNotFoundException {
        if (index == null || index < 0 || index >= rooms.size()) {
            throw new RoomNotFoundException("Invalid room index: " + index);
        }
        return rooms.get(index);
    }

    /**
     * Allocates a room to a resident.
     * Throws RoomNotFoundException or RoomFullException.
     */
    public void allocateRoom(Resident resident, String roomNumber)
            throws RoomNotFoundException, RoomFullException {
        Room room = findRoom(roomNumber);
        if (!room.isAvailable()) {
            throw new RoomFullException("Room " + roomNumber + " is already full.");
        }
        room.allocate();
        resident.setRoomNumber(roomNumber);
        System.out.println("[RoomManager] Room " + roomNumber
                + " allocated to " + resident.getName());
    }

    /** Vacates the resident's current room */
    public void vacateRoom(Resident resident) throws RoomNotFoundException {
        String roomNumber = resident.getRoomNumber();
        if (roomNumber.equals("UNASSIGNED")) {
            System.out.println("[RoomManager] Resident has no room to vacate.");
            return;
        }
        Room room = findRoom(roomNumber);
        room.vacate();
        resident.setRoomNumber("UNASSIGNED");
        System.out.println("[RoomManager] Room " + roomNumber
                + " vacated by " + resident.getName());
    }

    /**
     * Submits a room change request.
     * Overloaded submitChangeRequest — without reason
     */
    public void submitChangeRequest(Resident resident, String targetRoom) {
        RoomChangeRequest req = new RoomChangeRequest(
                resident.getUserId(), resident.getName(),
                resident.getRoomNumber(), targetRoom);
        changeRequests.add(req);
        System.out.println("[RoomManager] Room change request submitted: "
                + resident.getName() + " -> " + targetRoom);
    }

    /**
     * Overloaded submitChangeRequest — with reason
     */
    public void submitChangeRequest(Resident resident, String targetRoom, String reason)
            throws RoomNotFoundException {
        findRoom(targetRoom); // Validate target room exists
        RoomChangeRequest req = new RoomChangeRequest(
                resident.getUserId(), resident.getName(),
                resident.getRoomNumber(), targetRoom, reason);
        changeRequests.add(req);
        System.out.println("[RoomManager] Room change request submitted: "
                + resident.getName() + " -> " + targetRoom + " (Reason: " + reason + ")");
    }

    /** Admin approves a room change request */
    public void approveChangeRequest(String requestId)
            throws RoomNotFoundException, RoomFullException {
        for (RoomChangeRequest req : changeRequests) {
            if (req.getRequestId().equals(requestId)
                    && req.getStatus() == RoomChangeRequest.Status.PENDING) {
                Room target = findRoom(req.getRequestedRoom());
                if (!target.isAvailable()) {
                    throw new RoomFullException("Target room " + req.getRequestedRoom() + " is full.");
                }
                req.approve();
                System.out.println("[RoomManager] Request " + requestId + " approved.");
                return;
            }
        }
        System.out.println("[RoomManager] Request not found or not pending: " + requestId);
    }

    /** Admin rejects a room change request */
    public void rejectChangeRequest(String requestId) {
        for (RoomChangeRequest req : changeRequests) {
            if (req.getRequestId().equals(requestId)) {
                req.reject();
                System.out.println("[RoomManager] Request " + requestId + " rejected.");
                return;
            }
        }
        System.out.println("[RoomManager] Request not found: " + requestId);
    }

    /** Lists all rooms with their status */
    public void listAllRooms() {
        System.out.println("\n===== All Rooms =====");
        if (rooms.isEmpty()) {
            System.out.println("No rooms registered.");
        } else {
            for (Room r : rooms) r.displayInfo();
        }
        System.out.println("=====================\n");
    }

    /** Lists all room change requests */
    public void listChangeRequests() {
        System.out.println("\n===== Room Change Requests =====");
        if (changeRequests.isEmpty()) {
            System.out.println("No change requests found.");
        } else {
            for (RoomChangeRequest req : changeRequests) req.displayRequest();
        }
        System.out.println("================================\n");
    }

    /** Lists available rooms only */
    public void listAvailableRooms() {
        System.out.println("\n===== Available Rooms =====");
        boolean found = false;
        for (Room r : rooms) {
            if (r.isAvailable()) {
                r.displayInfo();
                found = true;
            }
        }
        if (!found) System.out.println("No available rooms.");
        System.out.println("===========================\n");
    }

    public List<Room> getAllRooms() { return rooms; }
}