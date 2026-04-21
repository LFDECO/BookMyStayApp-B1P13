import java.util.HashMap;
import java.util.Map;

class RoomInventory {

    private HashMap<String, Integer> inventory;

    // Constructor → Initialize inventory
    public RoomInventory() {
        inventory = new HashMap<>();
    }

    // Add / Register room type
    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    // Get availability of a specific room type
    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    // Update availability (increase/decrease)
    public void updateAvailability(String type, int change) {
        if (inventory.containsKey(type)) {
            int current = inventory.get(type);
            inventory.put(type, current + change);
        } else {
            System.out.println("Room type not found.");
        }
    }

    // Display all inventory
    public void displayInventory() {
        System.out.println("Current Room Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " → " + entry.getValue());
        }
    }
}

// Driver class
class UC3 {
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        // Register room types
        inventory.addRoomType("Single", 10);
        inventory.addRoomType("Double", 5);
        inventory.addRoomType("Suite", 2);

        // Display initial inventory
        inventory.displayInventory();

        // Update availability
        inventory.updateAvailability("Single", -2); // booked 2 rooms
        inventory.updateAvailability("Suite", 1);   // added 1 room

        // Check availability
        System.out.println("Available Single Rooms: " + inventory.getAvailability("Single"));

        // Display updated inventory
        inventory.displayInventory();
    }
}