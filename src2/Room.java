/**
 * Represents a hostel room.
 * Demonstrates: Overloaded constructors, Wrapper types
 */
public class Room {

    // Enum-like constants for room types
    public static final String TYPE_SINGLE = "SINGLE";
    public static final String TYPE_DOUBLE = "DOUBLE";
    public static final String TYPE_TRIPLE = "TRIPLE";

    private String roomNumber;      // Unique room identifier e.g. "A-101"
    private String roomType;        // SINGLE, DOUBLE, TRIPLE
    private Integer capacity;       // Max occupancy (Integer wrapper)
    private Integer currentOccupancy; // Current number of residents
    private boolean isAvailable;    // Whether room has space
    private String floor;           // Floor number/name
    private String block;           // Hostel block (A, B, C...)

    // Overloaded Constructor 1: Basic room
    public Room(String roomNumber, String roomType, int capacity) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.capacity = Integer.valueOf(capacity);   // Wrapper usage
        this.currentOccupancy = Integer.valueOf(0);
        this.isAvailable = true;
        this.floor = "1";
        this.block = "A";
    }

    // Overloaded Constructor 2: Full room details
    public Room(String roomNumber, String roomType, int capacity, String floor, String block) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.capacity = Integer.valueOf(capacity);
        this.currentOccupancy = Integer.valueOf(0);
        this.isAvailable = true;
        this.floor = floor;
        this.block = block;
    }

    /** Allocates room to a resident — increases occupancy */
    public boolean allocate() {
        if (currentOccupancy < capacity) {
            currentOccupancy++;
            isAvailable = currentOccupancy < capacity;
            return true;
        }
        return false; // Room is full
    }

    /** Vacates one slot from the room */
    public boolean vacate() {
        if (currentOccupancy > 0) {
            currentOccupancy--;
            isAvailable = true;
            return true;
        }
        return false;
    }

    // Getters
    public String getRoomNumber()       { return roomNumber; }
    public String getRoomType()         { return roomType; }
    public Integer getCapacity()        { return capacity; }
    public Integer getCurrentOccupancy(){ return currentOccupancy; }
    public boolean isAvailable()        { return isAvailable; }
    public String getFloor()            { return floor; }
    public String getBlock()            { return block; }

    public void displayInfo() {
        System.out.println("Room " + roomNumber + " [" + roomType + "] | Block: " + block
                + " | Floor: " + floor
                + " | Occupancy: " + currentOccupancy + "/" + capacity
                + " | " + (isAvailable ? "AVAILABLE" : "FULL"));
    }
}