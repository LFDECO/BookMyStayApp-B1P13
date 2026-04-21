import java.util.*;

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Inventory (with validation safety)
class RoomInventory {
    private HashMap<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, -1);
    }

    public void reduceAvailability(String type) throws InvalidBookingException {
        int available = getAvailability(type);

        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + type);
        }

        inventory.put(type, available - 1);
    }

    public boolean isValidRoomType(String type) {
        return inventory.containsKey(type);
    }
}

// Validator
class BookingValidator {

    public static void validate(String roomType, RoomInventory inventory)
            throws InvalidBookingException {

        if (roomType == null || roomType.isEmpty()) {
            throw new InvalidBookingException("Room type cannot be empty.");
        }

        if (!inventory.isValidRoomType(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        if (inventory.getAvailability(roomType) <= 0) {
            throw new InvalidBookingException("Room not available: " + roomType);
        }
    }
}

// Driver class
class UC9 {
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 1);
        inventory.addRoomType("Suite", 0);

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter room type to book: ");
        String roomType = sc.nextLine();

        try {
            // Validation (Fail-Fast)
            BookingValidator.validate(roomType, inventory);

            // Safe allocation
            inventory.reduceAvailability(roomType);

            System.out.println("Booking Successful!");

        } catch (InvalidBookingException e) {
            // Graceful failure
            System.out.println("Booking Failed: " + e.getMessage());
        }

        sc.close();
    }
}