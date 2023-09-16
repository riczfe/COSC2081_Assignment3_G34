import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Admin extends User {
    private List<PortManager> portManagers;

    public Admin(String username, String password) {
        super(username, password, "admin");
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
        // For example, you can read and display the contents of the vehicle.json file
        readVehicleJsonFile("/Users/erictran/eclipse-workspace/COSC2081_Assignment3_G34/src/vehicle.json");
    }
    
    

    public void readVehicleJsonFile(String vehicleJsonFilePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(vehicleJsonFilePath));
            StringBuilder jsonContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            String jsonString = jsonContent.toString();

            // You can parse and extract information from jsonString here
            // For example, you can print the entire JSON content for now
            System.out.println("Contents of vehicle.json:\n" + jsonString);

            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading vehicle.json file: " + e.getMessage());
        }
    }

    public void removePortManager(String username) {
        PortManager portManagerToRemove = null;
        
        for (PortManager portManager : portManagers) {
            if (portManager.getUsername().equals(username)) {
                portManagerToRemove = portManager;
                break; // Exit the loop once the PortManager is found
            }
        }

        if (portManagerToRemove != null) {
            portManagers.remove(portManagerToRemove);
            System.out.println("Port Manager with username " + username + " removed successfully.");
        } else {
            System.out.println("Port Manager with username " + username + " not found.");
        }
    }

    @Override
    public boolean login() {
        // Implement admin-specific login logic here
        // For example, check if the user is an admin based on their username or password
        if (getUsername().equals("admin") && getPassword().equals("admin123")) {
            System.out.println("Admin login successful!");
            return true;
        } else {
            System.out.println("Invalid username or password for admin.");
            return false;
        }
    }
}
