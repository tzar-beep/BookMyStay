import java.util.HashMap;
import java.util.Map;

/* ================================
   Room Domain Model
   ================================ */

abstract class Room {

    protected String roomType;
    protected int beds;
    protected double price;
    protected int size;

    public Room(String roomType, int beds, double price, int size) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
        this.size = size;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq ft");
        System.out.println("Price per night: $" + price);
    }
}

class SingleRoom extends Room {

    public SingleRoom() {
        super("Single", 1, 100.0, 200);
    }
}

class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double", 2, 180.0, 350);
    }
}

class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite", 3, 350.0, 600);
    }
}


/* ================================
   Centralized Inventory
   ================================ */

class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single", 10);
        inventory.put("Double", 5);
        inventory.put("Suite", 0);   // Example: Suite unavailable
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int change) {
        int current = inventory.getOrDefault(roomType, 0);
        inventory.put(roomType, current + change);
    }
}


/* ================================
   Search Service (Read-Only)
   ================================ */

class RoomSearchService {

    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void searchAvailableRooms(Room[] rooms) {

        System.out.println("Available Rooms");
        System.out.println("----------------------");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            if (available > 0) {

                room.displayRoomDetails();
                System.out.println("Available: " + available);
                System.out.println();
            }
        }
    }
}


/* ================================
   Application Entry
   ================================ */

public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("Hotel Booking System v1.0");
        System.out.println("--------------------------");

        // Create room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Store rooms in simple array
        Room[] rooms = {single, doubleRoom, suite};

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize search service
        RoomSearchService searchService = new RoomSearchService(inventory);

        // Guest searches available rooms
        searchService.searchAvailableRooms(rooms);
    }
}