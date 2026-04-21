import java.util.*;

// Room Domain Model
class Room {
    String type;
    double price;
    String amenities;

    public Room(String type, double price, String amenities) {
        this.type = type;
        this.price = price;
        this.amenities = amenities;
    }
}

// Inventory (from UC3)
class RoomInventory {
    private HashMap<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public Set<String> getAllRoomTypes() {
        return inventory.keySet(); // read-only access
    }
}

// Search Service (READ-ONLY)
class SearchService {

    public static void searchAvailableRooms(RoomInventory inventory, List<Room> rooms) {
        System.out.println("Available Rooms:\n");

        for (Room room : rooms) {
            int available = inventory.getAvailability(room.type);

            // Filter unavailable rooms (Defensive Programming)
            if (available > 0) {
                System.out.println("Room Type: " + room.type);
                System.out.println("Price: ₹" + room.price);
                System.out.println("Amenities: " + room.amenities);
                System.out.println("Available: " + available);
                System.out.println("--------------------------");
            }
        }
    }
}

// Driver Class
class UC4 {
    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 2);
        inventory.addRoomType("Double", 0);
        inventory.addRoomType("Suite", 1);

        // Room details (Domain Model)
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room("Single", 2000, "WiFi, AC"));
        rooms.add(new Room("Double", 3500, "WiFi, AC, TV"));
        rooms.add(new Room("Suite", 6000, "WiFi, AC, TV, Jacuzzi"));

        // Perform search (READ-ONLY)
        SearchService.searchAvailableRooms(inventory, rooms);
    }
}