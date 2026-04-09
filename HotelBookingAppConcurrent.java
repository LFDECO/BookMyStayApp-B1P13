import java.util.LinkedList;
import java.util.Queue;

/**
 * Demonstrates concurrent booking requests and thread-safe processing
 * using synchronization to avoid race conditions.
 * 
 * @author Siddhartha
 * @version 1.0
 */
class BookingSystem {

    // Shared mutable state
    private int availableRooms = 2; // limited rooms
    private final Queue<String> bookingQueue = new LinkedList<>();

    /**
     * Add booking request to shared queue
     */
    public synchronized void addRequest(String guestName) {
        bookingQueue.add(guestName);
        System.out.println(guestName + " added to booking queue.");
    }

    /**
     * Process booking request (critical section)
     */
    public synchronized void processRequest() {
        if (!bookingQueue.isEmpty()) {
            String guest = bookingQueue.poll();

            if (availableRooms > 0) {
                System.out.println(Thread.currentThread().getName() +
                        " allocating room to " + guest);

                // Simulate processing delay
                try { Thread.sleep(100); } catch (InterruptedException e) {}

                availableRooms--;
                System.out.println("Room booked for " + guest +
                        ". Remaining rooms: " + availableRooms);
            } else {
                System.out.println("No rooms available for " + guest);
            }
        }
    }
}

/**
 * Thread representing a booking processor
 */
class BookingProcessor extends Thread {

    private BookingSystem system;

    public BookingProcessor(BookingSystem system) {
        this.system = system;
    }

    @Override
    public void run() {
        // Each thread tries to process multiple requests
        for (int i = 0; i < 3; i++) {
            system.processRequest();
        }
    }
}

/**
 * Main class to simulate concurrent booking scenario
 */
public class HotelBookingAppConcurrent {

    public static void main(String[] args) {

        BookingSystem system = new BookingSystem();

        // Simulate multiple guests adding requests
        system.addRequest("Guest A");
        system.addRequest("Guest B");
        system.addRequest("Guest C");
        system.addRequest("Guest D");

        // Multiple threads processing bookings
        BookingProcessor t1 = new BookingProcessor(system);
        BookingProcessor t2 = new BookingProcessor(system);

        t1.setName("Processor-1");
        t2.setName("Processor-2");

        t1.start();
        t2.start();
    }
}