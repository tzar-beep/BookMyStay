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
   Centralized Inventory
   ================================ */

class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single", 10);
        inventory.put("Double", 5);
        inventory.put("Suite", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
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

    public void displayRequest() {
        System.out.println("Guest: " + guestName + " requested " + requestedRoomType + " room");
    }
}


/* ================================
   Booking Request Queue (FIFO)
   ================================ */

class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.println("Booking request added to queue for " + reservation.getGuestName());
    }

    public void displayQueue() {

        System.out.println("\nCurrent Booking Queue");
        System.out.println("---------------------");

        for (Reservation r : requestQueue) {
            r.displayRequest();
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

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Guests submit booking requests
        Reservation r1 = new Reservation("Alice", "Single");
        Reservation r2 = new Reservation("Bob", "Double");
        Reservation r3 = new Reservation("Charlie", "Suite");

        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Display queued requests
        bookingQueue.displayQueue();
    }
}