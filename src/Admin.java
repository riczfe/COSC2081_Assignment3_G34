import java.util.ArrayList;
import java.util.List;

public class Admin extends User {
    private List<PortManager> portManagers;

    public Admin(String username, String password) {
        super(username, password);
        this.portManagers = new ArrayList<>();
    }

    // Additional methods and logic
    public void addPortManager(PortManager portManager) {
        portManagers.add(portManager);
    }

    public void removePortManager(PortManager portManager) {
        portManagers.remove(portManager);
    }
    
    public boolean canMoveToPort(Vehicle vehicle, Port port) {
        // Implement logic to determine if a vehicle can successfully move to a port with its current load
        // For example, you can check if the port has landing ability and if the vehicle's weight is within the port's capacity
        return port.isLandingAbility() && vehicle.getWeight() <= port.getStoringCapacity();
    }

    public void refuelVehicle(Vehicle vehicle) {
        // Implement logic to refuel a vehicle
        // For example, you can set the vehicle's weight to its maximum capacity
        vehicle.setWeight(vehicle.getCapacity());
    }

    public void performStatisticsOperations(Port port) {
        // Implement various statistics operations on the given port
        // For example, you can calculate the average distance of trips in the port's traffic history
        double totalDistance = 0;
        int tripCount = 0;
        for (Trip trip : port.getTrafficHistory()) {
            totalDistance += trip.getDistance();
            tripCount++;
        }
        double averageDistance = totalDistance / tripCount;
        System.out.println("Average distance of trips: " + averageDistance);
    }
    
    
}