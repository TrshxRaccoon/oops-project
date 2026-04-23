package hostel;

import java.util.*;
import users.Resident;

public class HostelManager {

    private final List<Resident> residents = new ArrayList<>();
    private final Map<Integer, Room> rooms = new HashMap<>();
    private final Map<String, Integer> allocation = new HashMap<>();

    public void addResident(Resident r) {
        residents.add(r);
    }

    public void addRoom(int roomNo, int capacity) {
        rooms.put(roomNo, new Room(roomNo, capacity));
    }

    //added by unnat
    public Resident getResident(String id) {
        for (Resident r : residents) {
            if (r.getId().equals(id))
                return r;
        }
        return null;
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
        try {
            room.vacate();
            allocation.remove(residentId);
            System.out.println("Room vacated");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void showResidents() {
        if (residents.isEmpty()) {
             System.out.println("No residents");
             return;
        }
        for (Resident r : residents) {
            r.display();
        }
    }

    public void showRooms() {
        if (rooms.isEmpty()) {
            System.out.println("No rooms");
            return;
        }
        for (Room r : rooms.values()) {
            r.display();
        }
    }
}