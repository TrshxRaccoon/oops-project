import java.time.LocalDate;

/**
 * Represents a room change request submitted by a resident.
 */
public class RoomChangeRequest {

    public enum Status { PENDING, APPROVED, REJECTED } // Nested enum

    private static int requestCounter = 100;

    private String requestId;       // Unique request ID
    private String residentId;      // Requesting resident's ID
    private String residentName;    // Name of the requester
    private String currentRoom;     // Current room number
    private String requestedRoom;   // Desired room number
    private String reason;          // Reason for the request
    private Status status;          // Current status of the request
    private String requestDate;     // Date of submission
    private String resolvedDate;    // Date when resolved

    // Constructor 1: Basic request
    public RoomChangeRequest(String residentId, String residentName,
                              String currentRoom, String requestedRoom) {
        this.requestId = "RCR-" + requestCounter++;
        this.residentId = residentId;
        this.residentName = residentName;
        this.currentRoom = currentRoom;
        this.requestedRoom = requestedRoom;
        this.reason = "No reason provided.";
        this.status = Status.PENDING;
        this.requestDate = LocalDate.now().toString();
        this.resolvedDate = "Not yet resolved";
    }

    // Constructor 2: With reason
    public RoomChangeRequest(String residentId, String residentName,
                              String currentRoom, String requestedRoom, String reason) {
        this(residentId, residentName, currentRoom, requestedRoom);
        this.reason = reason;
    }

    /** Approves the request */
    public void approve() {
        this.status = Status.APPROVED;
        this.resolvedDate = LocalDate.now().toString();
    }

    /** Rejects the request */
    public void reject() {
        this.status = Status.REJECTED;
        this.resolvedDate = LocalDate.now().toString();
    }

    // Getters
    public String getRequestId()    { return requestId; }
    public String getResidentId()   { return residentId; }
    public String getResidentName() { return residentName; }
    public String getCurrentRoom()  { return currentRoom; }
    public String getRequestedRoom(){ return requestedRoom; }
    public String getReason()       { return reason; }
    public Status getStatus()       { return status; }
    public String getRequestDate()  { return requestDate; }

    public void displayRequest() {
        System.out.println("[" + requestId + "] " + residentName
                + " | From: " + currentRoom + " -> To: " + requestedRoom
                + " | Reason: " + reason
                + " | Status: " + status
                + " | Date: " + requestDate);
    }
}