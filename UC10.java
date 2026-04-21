import java.util.*;

// Reservation
class Reservation {
    String reservationID;
    String roomType;

    public Reservation(String reservationID, String roomType) {
        this.reservationID = reservationID;
        this.roomType = roomType;
    }
}

// Inventory Service
class RoomInventory {
    private HashMap<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public void increaseAvailability(String type) {
        inventory.put(type, inventory.get(type) + 1);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }
}

// Booking History (simplified with Map for lookup)
class BookingHistory {
    HashMap<String, Reservation> confirmedBookings = new HashMap<>();

    public void addReservation(Reservation r) {
        confirmedBookings.put(r.reservationID, r);
    }

    public Reservation getReservation(String id) {
        return confirmedBookings.get(id);
    }

    public void removeReservation(String id) {
        confirmedBookings.remove(id);
    }
}

// Cancellation Service
class CancellationService {

    private Stack<String> rollbackStack = new Stack<>(); // LIFO

    public void cancelBooking(String reservationID,
                             BookingHistory history,
                             RoomInventory inventory) {

        // Validate existence
        Reservation r = history.getReservation(reservationID);

        if (r == null) {
            System.out.println("Cancellation Failed: Invalid Reservation ID");
            return;
        }

        // Push to rollback stack
        rollbackStack.push(reservationID);

        // Restore inventory
        inventory.increaseAvailability(r.roomType);

        // Remove from history
        history.removeReservation(reservationID);

        // Confirmation
        System.out.println("Cancellation Successful for ID: " + reservationID);
    }

    // Optional: show rollback history
    public void showRollbackHistory() {
        System.out.println("Rollback Stack: " + rollbackStack);
    }
}

// Driver Class
class UC10 {
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 1);

        BookingHistory history = new BookingHistory();

        // Simulate confirmed booking
        Reservation r1 = new Reservation("SI1", "Single");
        history.addReservation(r1);

        CancellationService service = new CancellationService();

        // Cancel booking
        service.cancelBooking("SI1", history, inventory);

        // Try invalid cancellation
        service.cancelBooking("SI1", history, inventory);

        // Show rollback stack
        service.showRollbackHistory();

        // Check inventory restored
        System.out.println("Available Single Rooms: " +
                inventory.getAvailability("Single"));
    }
}