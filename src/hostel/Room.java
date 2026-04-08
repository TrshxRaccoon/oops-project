package hostel;

public class Room {
    private int roomNumber;
    private int capacity;
    private int occupants;

    public Room(int roomNumber, int capacity) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.occupants = 0;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public boolean isFull() {
        return (occupants >= capacity);
    }

    public void allocate() throws Exception {
        if (isFull()) {
            throw new Exception("Room is full");
        }
        occupants++;
    }

    public void vacate() {
        if (occupants > 0) {
            occupants--;
        }
    }

    public void display() {
        System.out.println("Room "+roomNumber+" | Capacity: "+capacity);
    }
}