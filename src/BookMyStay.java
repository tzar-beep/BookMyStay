import java.util.*;

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
   Inventory Service
   ================================ */

class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrement(String roomType) {

        int current = inventory.get(roomType);
        inventory.put(roomType, current - 1);
    }

    public void displayInventory() {

        System.out.println("\nRemaining Inventory");
        System.out.println("--------------------");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}


/* ================================
   Reservation Request
   ================================ */

class Reservation {

    private String guestName;
    private String requestedRoomType;

    public Reservation(String guestName, String requestedRoomType) {
        this.guestName = guestName;
        this.requestedRoomType = requestedRoomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRequestedRoomType() {
        return requestedRoomType;
    }
}


/* ================================
   Booking Request Queue
   ================================ */

class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}


/* ================================
   Booking Service (Allocation)
   ================================ */

class BookingService {

    private RoomInventory inventory;

    // Track all allocated room IDs
    private Set<String> allocatedRoomIds;

    // Map room type → assigned rooms
    private Map<String, Set<String>> roomAssignments;

    // Counter for unique room IDs
    private int roomCounter = 1;

    public BookingService(RoomInventory inventory) {

        this.inventory = inventory;

        allocatedRoomIds = new HashSet<>();
        roomAssignments = new HashMap<>();
    }

    public void processBookings(BookingRequestQueue queue) {

        while (!queue.isEmpty()) {

            Reservation request = queue.getNextRequest();

            String type = request.getRequestedRoomType();

            int available = inventory.getAvailability(type);

            if (available <= 0) {

                System.out.println("No " + type + " rooms available for " + request.getGuestName());
                continue;
            }

            // Generate unique room ID
            String roomId = type.substring(0,1).toUpperCase() + roomCounter++;

            // Ensure uniqueness
            if (allocatedRoomIds.contains(roomId)) {
                System.out.println("Duplicate room ID detected!");
                continue;
            }

            allocatedRoomIds.add(roomId);

            // Store assignment
            roomAssignments
                    .computeIfAbsent(type, k -> new HashSet<>())
                    .add(roomId);

            // Update inventory
            inventory.decrement(type);

            // Confirm reservation
            System.out.println("Reservation confirmed for "
                    + request.getGuestName()
                    + " → Room ID: " + roomId);
        }
    }

    public void displayAssignments() {

        System.out.println("\nRoom Allocations");
        System.out.println("-----------------");

        for (Map.Entry<String, Set<String>> entry : roomAssignments.entrySet()) {

            System.out.println(entry.getKey() + " Rooms: " + entry.getValue());
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

        RoomInventory inventory = new RoomInventory();

        BookingRequestQueue queue = new BookingRequestQueue();

        // Booking requests
        queue.addRequest(new Reservation("Alice", "Single"));
        queue.addRequest(new Reservation("Bob", "Double"));
        queue.addRequest(new Reservation("Charlie", "Suite"));
        queue.addRequest(new Reservation("David", "Single"));

        BookingService bookingService = new BookingService(inventory);

        // Process requests
        bookingService.processBookings(queue);

        // Show allocation results
        bookingService.displayAssignments();

        // Show remaining inventory
        inventory.displayInventory();
    }
}