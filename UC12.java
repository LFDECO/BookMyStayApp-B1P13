import java.io.*;
import java.util.*;

// Reservation (Serializable)
class Reservation implements Serializable {
    String reservationID;
    String roomType;

    public Reservation(String reservationID, String roomType) {
        this.reservationID = reservationID;
        this.roomType = roomType;
    }
}

// Inventory (Serializable)
class RoomInventory implements Serializable {
    HashMap<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }
}

// Booking History (Serializable)
class BookingHistory implements Serializable {
    List<Reservation> history = new ArrayList<>();

    public void addReservation(Reservation r) {
        history.add(r);
    }
}

// Wrapper class to store entire system state
class SystemState implements Serializable {
    RoomInventory inventory;
    BookingHistory history;

    public SystemState(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    // Save state (Serialization)
    public static void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving system state.");
        }
    }

    // Load state (Deserialization)
    public static SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("System state loaded successfully.");
            return (SystemState) ois.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("No saved state found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("Error loading state. Starting with safe defaults.");
        }

        // Safe fallback (failure tolerance)
        return new SystemState(new RoomInventory(), new BookingHistory());
    }
}

// Driver Class
class UC12 {
    public static void main(String[] args) {

        // LOAD (simulate system restart)
        SystemState state = PersistenceService.load();

        // If fresh start, initialize some data
        if (state.inventory.inventory.isEmpty()) {
            state.inventory.addRoomType("Single", 5);
            state.inventory.addRoomType("Suite", 2);

            state.history.addReservation(new Reservation("SI1", "Single"));
        }

        // SHOW current state
        System.out.println("\nCurrent Inventory: " + state.inventory.inventory);
        System.out.println("Booking History Count: " + state.history.history.size());

        // SAVE before shutdown
        PersistenceService.save(state);
    }
}