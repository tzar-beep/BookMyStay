
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

    public void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq ft");
        System.out.println("Price per night: $" + price);
    }
}

class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 100.0, 200);
    }
}

class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 180.0, 350);
    }
}

class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 350.0, 600);
    }
}

public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("Hotel Booking System v1.0");
        System.out.println("--------------------------------");

        // Create room objects
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Availability variables (simple variables, no data structures yet)
        int singleAvailable = 10;
        int doubleAvailable = 5;
        int suiteAvailable = 2;

        // Display room details
        singleRoom.displayRoomDetails();
        System.out.println("Available: " + singleAvailable);
        System.out.println();

        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleAvailable);
        System.out.println();

        suiteRoom.displayRoomDetails();
        System.out.println("Available: " + suiteAvailable);
    }
}