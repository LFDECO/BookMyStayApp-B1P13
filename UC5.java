import java.util.*;

// Reservation class → represents a booking request
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void display() {
        System.out.println("Guest: " + guestName + ", Room Type: " + roomType);
    }
}

// Booking Request Queue
class BookingQueue {
    private Queue<Reservation> queue;

    public BookingQueue() {
        queue = new LinkedList<>(); // FIFO structure
    }

    // Add request (enqueue)
    public void addRequest(Reservation r) {
        queue.offer(r);
        System.out.println("Booking request added for " + r.guestName);
    }

    // View all pending requests (no removal)
    public void displayQueue() {
        System.out.println("\nPending Booking Requests:");
        for (Reservation r : queue) {
            r.display();
        }
    }

    // Get next request (for future UC)
    public Reservation getNextRequest() {
        return queue.peek(); // does NOT remove
    }
}

// Driver class
class UC5 {
    public static void main(String[] args) {

        BookingQueue bookingQueue = new BookingQueue();

        // Add booking requests (arrival order matters)
        bookingQueue.addRequest(new Reservation("Alice", "Single"));
        bookingQueue.addRequest(new Reservation("Bob", "Suite"));
        bookingQueue.addRequest(new Reservation("Charlie", "Double"));

        // Display queue (FIFO order)
        bookingQueue.displayQueue();

        // Show next request (without removing)
        System.out.println("\nNext request to process:");
        Reservation next = bookingQueue.getNextRequest();
        if (next != null) {
            next.display();
        }
    }
}