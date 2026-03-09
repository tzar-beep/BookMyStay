import java.util.HashMap;
import java.util.Map;

/**
 * Abstract Room class representing common room attributes.
 */
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

/**
 * Single Room
 */
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single", 1, 100.0, 200);
    }
}

/**
 * Double Room
 */
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double", 2, 180.0, 350);
    }
}

/**
 * Suite Room
 */
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite", 3, 350.0, 600);
    }
}

/**
 * Centralized Room Inventory Manager
 */
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        // Initialize availability
        inventory.put("Single", 10);
        inventory.put("Double", 5);
        inventory.put("Suite", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int change) {

        int current = inventory.getOrDefault(roomType, 0);
        inventory.put(roomType, current + change);
    }

    public void displayInventory() {

        System.out.println("Current Room Inventory");
        System.out.println("----------------------");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " Rooms Available: " + entry.getValue());
        }
    }
}

/**
 * Application Entry Point
 */
public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("Hotel Booking System v1.0");
        System.out.println("---------------------------");

        // Create rooms
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display room details with availability
        single.displayRoomDetails();
        System.out.println("Available: " + inventory.getAvailability(single.getRoomType()));
        System.out.println();

        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + inventory.getAvailability(doubleRoom.getRoomType()));
        System.out.println();

        suite.displayRoomDetails();
        System.out.println("Available: " + inventory.getAvailability(suite.getRoomType()));
        System.out.println();

        // Display full inventory
        inventory.displayInventory();
    }
}