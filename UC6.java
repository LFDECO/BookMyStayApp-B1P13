import java.util.*;

// Reservation (from UC5)
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Inventory Service (from UC3)
class RoomInventory {
    private HashMap<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void reduceAvailability(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }
}

// Booking Queue (from UC5)
class BookingQueue {
    Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll(); // removes from queue (FIFO)
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// Booking Service (UC6 core logic)
class BookingService {

    private Set<String> allocatedRoomIDs = new HashSet<>();
    private HashMap<String, Set<String>> roomAllocations = new HashMap<>();

    // Generate unique room ID
    private String generateRoomID(String type, int count) {
        return type.substring(0, 2).toUpperCase() + count;
    }

    public void processBookings(BookingQueue queue, RoomInventory inventory) {

        while (!queue.isEmpty()) {
            Reservation r = queue.getNextRequest();

            int available = inventory.getAvailability(r.roomType);

            if (available > 0) {

                // Generate unique ID
                String roomID = generateRoomID(r.roomType, allocatedRoomIDs.size() + 1);

                // Ensure uniqueness
                if (!allocatedRoomIDs.contains(roomID)) {

                    allocatedRoomIDs.add(roomID);

                    // Map room type → allocated IDs
                    roomAllocations.putIfAbsent(r.roomType, new HashSet<>());
                    roomAllocations.get(r.roomType).add(roomID);

                    // Update inventory immediately
                    inventory.reduceAvailability(r.roomType);

                    // Confirm booking
                    System.out.println("Booking Confirmed!");
                    System.out.println("Guest: " + r.guestName);
                    System.out.println("Room Type: " + r.roomType);
                    System.out.println("Allocated Room ID: " + roomID);
                    System.out.println("--------------------------");

                }

            } else {
                System.out.println("Booking Failed for " + r.guestName + " (No rooms available)");
            }
        }
    }
}

// Driver Class
class UC6 {
    public static void main(String[] args) {

        // Setup inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 2);
        inventory.addRoomType("Suite", 1);

        // Setup queue
        BookingQueue queue = new BookingQueue();
        queue.addRequest(new Reservation("Alice", "Single"));
        queue.addRequest(new Reservation("Bob", "Single"));
        queue.addRequest(new Reservation("Charlie", "Single")); // should fail

        // Process bookings
        BookingService service = new BookingService();
        service.processBookings(queue, inventory);
    }
}