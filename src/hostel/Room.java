package hostel;

public class Room 
{
    private final int roomNumber;
    private int capacity;
    private int occupants;

    public Room(int roomNumber, int capacity) 
    {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.occupants = 0;
    }

    public int getRoomNumber() 
    {
        return roomNumber;
    }

    public boolean isFull() 
    {
        return (occupants >= capacity);
    }

    public void allocate() throws Exception 
    {
        if (isFull()) 
        {
            throw new Exception("Room is full");
        }
        occupants++;
        capacity--;
    }

    public void vacate() throws Exception
    {
        if (occupants == 0)
        {
            throw new Exception("Cannot vacate as Room " + roomNumber + " is already empty");
        }
        occupants--;
        capacity++;
    }

    public void display() 
    {
        System.out.println("Room " + roomNumber + " | Capacity: " + capacity);
    }
}
