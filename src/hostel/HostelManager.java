package hostel;

import java.util.*;
import users.Resident;

public class HostelManager {

    private List<Resident> residents = new ArrayList<>();
    private Map<Integer, Room> rooms = new HashMap<>();
    private Map<String, Integer> allocation = new HashMap<>();

    public void addResident(Resident r) {
        residents.add(r);
    }

    public void addRoom(int roomNo, int capacity) {
        rooms.put(roomNo, new Room(roomNo, capacity));
    }

    public void allocateRoom(String residentId, int roomNo) {
        Room room = rooms.get(roomNo);

        if (room == null) {
            System.out.println("Room does not exist!");
            return;
        }

        try {
            room.allocate();
            allocation.put(residentId, roomNo);
            System.out.println("Room allocated successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void vacateRoom(String residentId) {
        Integer roomNo = allocation.get(residentId);

        if (roomNo == null) {
            System.out.println("No room assigned");
            return;
        }

        Room room = rooms.get(roomNo);
        room.vacate();
        allocation.remove(residentId);

        System.out.println("Room vacated");
    }

    public void showResidents() {
        for (Resident r : residents) {
            r.display();
        }
    }

    public void showRooms() {
        for (Room r : rooms.values()) {
            r.display();
        }
    }
}