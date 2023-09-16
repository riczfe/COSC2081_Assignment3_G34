import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
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

        // Update the account.json file
        updateAccountJsonFile(portManager);
    }

    private void updateAccountJsonFile(PortManager portManager) {
        String accountJsonFilePath = "/Users/erictran/eclipse-workspace/COSC2081_Assignment3_G34/src/account.json";

        try {
            // Read the existing JSON content
            BufferedReader reader = new BufferedReader(new FileReader(accountJsonFilePath));
            StringBuilder jsonContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            String jsonString = jsonContent.toString();

            // Modify the JSON string to include the new Port Manager account
            String newAccount = String.format("{\"username\":\"%s\",\"password\":\"%s\",\"role\":\"portManager\",\"portId\":\"%s\"}",
                    portManager.getUsername(), portManager.getPassword(), portManager.getPortId());

            // Find the position of the last ']' character in the JSON string
            int lastIndex = jsonString.lastIndexOf("]");

            if (lastIndex != -1) {
                // Insert a comma before adding the new account if it's not the first account
                if (lastIndex > 0) {
                    jsonString = jsonString.substring(0, lastIndex) + "," + newAccount + jsonString.substring(lastIndex);
                } else {
                    jsonString = "[" + newAccount + "]";
                }

                // Write the updated JSON back to the file
                FileWriter fileWriter = new FileWriter(accountJsonFilePath);
                fileWriter.write(jsonString);
                fileWriter.close();

            } else {
                System.err.println("Invalid JSON format in account.json");
            }

        } catch (IOException e) {
            System.err.println("Error updating account.json file: " + e.getMessage());
        }
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

    public void performStatisticsOperations() {
        String vehicleJsonFilePath = "/Users/erictran/eclipse-workspace/COSC2081_Assignment3_G34/src/vehicle.json";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(vehicleJsonFilePath));
            StringBuilder jsonContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            String jsonString = jsonContent.toString();

            // Split the JSON string into individual records
            String[] records = jsonString.split("\\},\\s*\\{");

            // Process and display each record
            for (String record : records) {
                // Format and display the record
                System.out.println("Record:");
                String[] fields = record.split(",");
                for (String field : fields) {
                    String[] keyValue = field.split(":");
                    if (keyValue.length == 2) {
                        String key = keyValue[0].replaceAll("[\"{}]", "").trim();
                        String value = keyValue[1].replaceAll("[\"{}]", "").trim();
                        System.out.println(key + ": " + value);
                    }
                }
                System.out.println();
            }

            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading vehicle.json file: " + e.getMessage());
        }
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
