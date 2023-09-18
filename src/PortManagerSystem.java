import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PortManagerSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Port port = initializePortFromJson("/Users/erictran/eclipse-workspace/COSC2081_Assignment3_G34/src/account.json");

        System.out.println("Welcome to the Port Management System!");
        System.out.print("Enter 2a1: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        User user = findUser(username, password, port);

        if (user != null) {
            System.out.println("Debug: User role = " + user.getRole()); // Debug statement

            if (user.getRole().equals("admin")) { // Update role comparison here
                // User is an admin
                Admin admin = new Admin(username, password);
                displayAdminMenu(admin, port, scanner);
            } else if (user.getRole().equals("portManager") || user.getUsername().startsWith("manager")) {
                // User is a Port Manager
                PortManager portManager = (PortManager) user;
                displayPortManagerMenu(portManager, port, scanner);
            } else {
                System.out.println("Invalid user type. Exiting.");
            }
        } else {
            System.out.println("Invalid username or password.");
        }

        scanner.close();
    }

    private static User findUser(String username, String password, Port port) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("/Users/erictran/eclipse-workspace/COSC2081_Assignment3_G34/src/account.json"));
            StringBuilder jsonContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            String jsonString = jsonContent.toString();
            int startIndex = jsonString.indexOf('{');
            int endIndex = jsonString.lastIndexOf('}');

            if (startIndex != -1 && endIndex != -1) {
                String accountString = jsonString.substring(startIndex, endIndex + 1);

                // Replace line breaks and whitespace to make parsing easier
                accountString = accountString.replaceAll("\\s+", "");

                // Split accounts based on "},{" separator
                String[] accounts = accountString.split("\\},\\{");

                for (String account : accounts) {
                    String storedUsername = extractValue1(account, "\"username\":\"");
                    String storedPassword = extractValue1(account, "\"password\":\"");
                    String storedRole = extractValue1(account, "\"role\":\"");

                    if (username.equals(storedUsername) && password.equals(storedPassword)) {
                        if ("admin".equals(storedRole)) {
                            return new Admin(username, password);
                        } else if ("portManager".equals(storedRole)) {
                            String portId = extractValue1(account, "\"portId\":\"");
                            PortManager portManager = new PortManager(username, password, port);
                            portManager.setPortId(portId);
                            return portManager;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
        }

        return null;
    }

    private static String extractValue1(String jsonString, String key) {
        int startIndex = jsonString.indexOf(key) + key.length();
        int endIndex = jsonString.indexOf("\"", startIndex);
        if (startIndex != -1 && endIndex != -1) {
            return jsonString.substring(startIndex, endIndex);
        }
        return "";
    }

    private static void displayAdminMenu(Admin admin, Port port, Scanner scanner) {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add Port Manager");
            System.out.println("2. Remove Port Manager");
            System.out.println("3. Add Vehicle");
            System.out.println("4. Remove Vehicle");
            System.out.println("5. Add Container");
            System.out.println("6. Remove Container");
            System.out.println("7. Add Port");
            System.out.println("8. Remove Port");
            System.out.println("9. View Port Statistics");
            System.out.println("10. Log Out");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter Port Manager's username: ");
                    String pmUsername = scanner.nextLine();
                    System.out.print("Enter Port Manager's password: ");
                    String pmPassword = scanner.nextLine();
                    
                    // Create a new PortManager and add it to the admin's list
                    PortManager newPortManager = new PortManager(pmUsername, pmPassword, port);
                    admin.addPortManager(newPortManager);
                    System.out.println("Port Manager added successfully.");
                    break;
                case 2:
                    System.out.print("Enter the username of the Port Manager to remove: ");
                    String pmUsernameToRemove = scanner.nextLine();
                    admin.removePortManager(pmUsernameToRemove);
                    break;
                case 3:
                	admin.addVehicle();
                    break;
                case 4:
                	System.out.print("Enter the ID of the Vehicle to remove: ");
                    String vehicleIdToRemove = scanner.nextLine();
                    admin.removeVehicle(vehicleIdToRemove);
                    break;
                case 5:
                    admin.addContainer();
                    break;
                case 6:
                    // Implement logic to remove a container
                	System.out.print("Enter the ID of the Container to remove: ");
                    String containerIdToRemove = scanner.nextLine();
                    admin.removeContainer(containerIdToRemove);
                    break;
                case 7:
                    // Option to add a port
                    System.out.print("Enter Port ID: ");
                    String portId = scanner.nextLine();
                    System.out.print("Enter Port Name: ");
                    String portName = scanner.nextLine();
                    System.out.print("Enter Latitude: ");
                    double latitude = scanner.nextDouble();
                    System.out.print("Enter Longitude: ");
                    double longitude = scanner.nextDouble();
                    System.out.print("Enter Storing Capacity: ");
                    int storingCapacity = scanner.nextInt();
                    System.out.print("Enter Landing Ability (true/false): ");
                    boolean landingAbility = scanner.nextBoolean();
                    System.out.print("Enter Container Count: ");
                    int containerCount = scanner.nextInt();
                    System.out.print("Enter Vehicle Count: ");
                    int vehicleCount = scanner.nextInt();
                    System.out.print("Enter Fuel Consumption: ");
                    double fuelConsumption = scanner.nextDouble();
                    // Create a new Port object and add it using the Admin's addPort method
                    Port newPort = new Port(portId, portName, latitude, longitude, storingCapacity, landingAbility, containerCount, vehicleCount, fuelConsumption);
                    admin.addPort(newPort);
                    System.out.println("Port added successfully.");
                    break;
                case 8:
                	System.out.print("Enter the ID of the Port to remove: ");
                    String portIdToRemove = scanner.nextLine();
                    
                    // Assuming the method doesn't return a boolean
                    admin.removePort(portIdToRemove);
                    
                    // Print a message indicating that the port removal was requested
                    System.out.println("Port removal requested for ID " + portIdToRemove);
                    break;
                case 9:
                    admin.performStatisticsOperations();
                    break;
                case 10:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    private static void displayPortManagerMenu(PortManager portManager, Port port, Scanner scanner) {
        while (true) {
            System.out.println("\nPort Manager Menu:");
            System.out.println("1. Add Container");
            System.out.println("2. Remove Container");
            System.out.println("3. Add Vehicle");
            System.out.println("4. Remove Vehicle");
            System.out.println("5. View Traffic History");
            System.out.println("6. Log Out");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    // Implement logic to add a container
                    break;
                case 2:
                    System.out.print("Enter the ID of the Container to remove: ");
                    String containerIdToRemove = scanner.nextLine();
                    removeContainer(port, containerIdToRemove);
                    break;
                case 3:
                    // Implement logic to add a vehicle
                    break;
                case 4:
                    // Implement logic to remove a vehicle
                    break;
                case 5:
                    port.printTrafficHistory();
                    break;
                case 6:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void removeContainer(Port port, String containerIdToRemove) {
        List<Container> containers = port.getContainerList();
        Container containerToRemove = null;

        for (Container container : containers) {
            if (container.getId().equals(containerIdToRemove)) {
                containerToRemove = container;
                break;
            }
        }

        if (containerToRemove != null) {
            containers.remove(containerToRemove);
            System.out.println("Container with ID " + containerIdToRemove + " removed successfully.");
        } else {
            System.out.println("Container with ID " + containerIdToRemove + " not found.");
        }
    }

    private static Port initializePortFromJson(String jsonFilePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(jsonFilePath));
            StringBuilder jsonContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            String jsonString = jsonContent.toString();

            // Find the index of the "ports" array
            int portsIndex = jsonString.indexOf("\"ports\": [");
            if (portsIndex != -1) {
                // Find the start and end of the first port object within the "ports" array
                int portStartIndex = jsonString.indexOf("{", portsIndex);
                int portEndIndex = jsonString.indexOf("}", portStartIndex);

                if (portStartIndex != -1 && portEndIndex != -1) {
                    String portObject = jsonString.substring(portStartIndex, portEndIndex + 1);

                    // Extract values manually
                    String id = extractValue1(portObject, "\"id\":\"");
                    String name = extractValue1(portObject, "\"name\":\"");
                    double latitude = Double.parseDouble(extractValue1(portObject, "\"latitude\":"));
                    double longitude = Double.parseDouble(extractValue1(portObject, "\"longitude\":"));
                    int storingCapacity = Integer.parseInt(extractValue1(portObject, "\"storingCapacity\":"));
                    boolean landingAbility = Boolean.parseBoolean(extractValue1(portObject, "\"landingAbility\":"));
                    int containerCount = Integer.parseInt(extractValue1(portObject, "\"containerCount\":"));
                    int vehicleCount = Integer.parseInt(extractValue1(portObject, "\"vehicleCount\":"));
                    double fuelConsumption = Double.parseDouble(extractValue1(portObject, "\"fuelConsumption\":"));

                    // Create the Port object
                    Port port = new Port(id, name, latitude, longitude, storingCapacity, landingAbility, containerCount, vehicleCount, fuelConsumption);
                    return port;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
        }
        

        return null;
    }

    // Extract a value from a JSON string based on a key
    private static String extractValue(String jsonString, String key) {
        int startIndex = jsonString.indexOf(key) + key.length();
        int endIndex = jsonString.indexOf("\"", startIndex);
        if (startIndex != -1 && endIndex != -1) {
            return jsonString.substring(startIndex, endIndex);
        }
        return "";
    }
}
