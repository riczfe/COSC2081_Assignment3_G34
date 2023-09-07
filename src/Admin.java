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
        // Check if the vehicle can successfully move to the given port based on its current load
        // Implement your logic here
        return true; // Replace with your actual implementation
    }

    public void refuelVehicle(Vehicle vehicle) {
        // Refuel the given vehicle
        // Implement your logic here
    }

    public void performStatisticsOperations() {
        // Perform various statistics operations
        // Implement your logic here
    }
}