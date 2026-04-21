import java.util.*;

// Reservation (reuse from UC6, simplified)
class Reservation {
    String guestName;
    String roomType;
    String reservationID;

    public Reservation(String guestName, String roomType, String reservationID) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.reservationID = reservationID;
    }

    public void display() {
        System.out.println("ID: " + reservationID +
                ", Guest: " + guestName +
                ", Room: " + roomType);
    }
}

// Booking History → stores confirmed bookings
class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    // Add confirmed booking
    public void addReservation(Reservation r) {
        history.add(r); // preserves order
    }

    // Retrieve all bookings
    public List<Reservation> getAllReservations() {
        return history; // read-only usage expected
    }
}

// Reporting Service
class BookingReportService {

    // Display full booking history
    public void showAllBookings(List<Reservation> history) {
        System.out.println("\nBooking History:");
        for (Reservation r : history) {
            r.display();
        }
    }

    // Generate summary report
    public void generateSummary(List<Reservation> history) {
        HashMap<String, Integer> roomCount = new HashMap<>();

        for (Reservation r : history) {
            roomCount.put(r.roomType,
                    roomCount.getOrDefault(r.roomType, 0) + 1);
        }

        System.out.println("\nBooking Summary (Room-wise):");
        for (String type : roomCount.keySet()) {
            System.out.println(type + " → " + roomCount.get(type));
        }

        System.out.println("Total Bookings: " + history.size());
    }
}

// Driver class
class UC8 {
    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulating confirmed bookings (from UC6)
        history.addReservation(new Reservation("Alice", "Single", "SI1"));
        history.addReservation(new Reservation("Bob", "Suite", "SU2"));
        history.addReservation(new Reservation("Charlie", "Single", "SI3"));

        // Admin views history
        reportService.showAllBookings(history.getAllReservations());

        // Admin generates summary report
        reportService.generateSummary(history.getAllReservations());
    }
}