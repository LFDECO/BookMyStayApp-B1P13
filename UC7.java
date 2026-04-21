import java.util.*;

// Add-On Service (represents optional feature)
class AddOnService {
    String name;
    double cost;

    public AddOnService(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }
}

// Add-On Service Manager
class AddOnServiceManager {

    // Map → Reservation ID → List of Services
    private HashMap<String, List<AddOnService>> serviceMap = new HashMap<>();

    // Add service to a reservation
    public void addService(String reservationID, AddOnService service) {
        serviceMap.putIfAbsent(reservationID, new ArrayList<>());
        serviceMap.get(reservationID).add(service);

        System.out.println("Service added: " + service.name + " for Reservation " + reservationID);
    }

    // Display services for a reservation
    public void displayServices(String reservationID) {
        List<AddOnService> services = serviceMap.get(reservationID);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        System.out.println("\nServices for Reservation " + reservationID + ":");
        for (AddOnService s : services) {
            System.out.println(s.name + " - ₹" + s.cost);
        }
    }

    // Calculate total cost of add-ons
    public double calculateTotalCost(String reservationID) {
        List<AddOnService> services = serviceMap.get(reservationID);

        double total = 0;

        if (services != null) {
            for (AddOnService s : services) {
                total += s.cost;
            }
        }

        return total;
    }
}

// Driver class
class UC7 {
    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        // Assume reservation already exists (from UC6)
        String reservationID = "SI1";

        // Add services
        manager.addService(reservationID, new AddOnService("Breakfast", 500));
        manager.addService(reservationID, new AddOnService("Airport Pickup", 1000));
        manager.addService(reservationID, new AddOnService("Extra Bed", 800));

        // Display services
        manager.displayServices(reservationID);

        // Calculate total cost
        double total = manager.calculateTotalCost(reservationID);
        System.out.println("\nTotal Add-On Cost: ₹" + total);
    }
}