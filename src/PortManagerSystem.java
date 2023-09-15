import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class PortManagerSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Port port = initializePortFromJson("/Users/erictran/eclipse-workspace/COSC2081_Assignment3_G34/src/vehicle.json"); // Initialize the port with data from JSON

        System.out.println("Welcome to the Port Management System!");
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        User user = new User(username, password);
        Admin admin = new Admin(username, password);

        if (user.login()) {
            if (admin.login()) {
                // User is an admin
                displayAdminMenu(admin, port, scanner);
            } else if (user instanceof PortManager) {
                PortManager portManager = (PortManager) user;
                displayPortManagerMenu(portManager, port, scanner);
            } else {
                System.out.println("Invalid user type. Exiting.");
                return;
            }
        }

        scanner.close();
    }

    private static void displayAdminMenu(Admin admin, Port port, Scanner scanner) {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add Port Manager");
            System.out.println("2. Remove Port Manager");
            System.out.println("3. View Port Statistics");
            System.out.println("4. Log Out");
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
                    admin.performStatisticsOperations(port);
                    break;
                case 4:
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
                    // Implement logic to remove a container
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

    private static Port initializePortFromJson(String jsonFilePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(jsonFilePath));
            StringBuilder jsonContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            // Manually parse the JSON content
            String jsonString = jsonContent.toString();
            String id = extractValue(jsonString, "\"id\":");
            String name = extractValue(jsonString, "\"name\":");
            double latitude = Double.parseDouble(extractValue(jsonString, "\"latitude\":"));
            double longitude = Double.parseDouble(extractValue(jsonString, "\"longitude\":"));
            int storingCapacity = Integer.parseInt(extractValue(jsonString, "\"storingCapacity\":"));
            boolean landingAbility = Boolean.parseBoolean(extractValue(jsonString, "\"landingAbility\":"));

            // Create the Port object
            Port port = new Port(id, name, latitude, longitude, storingCapacity, landingAbility);
            return port;
        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
            return null;
        }
    }

    private static String extractValue(String jsonString, String key) {
        int startIndex = jsonString.indexOf(key) + key.length();
        int endIndex = jsonString.indexOf(",", startIndex);

        if (endIndex == -1) {
            endIndex = jsonString.indexOf("}", startIndex);
        }

        return jsonString.substring(startIndex, endIndex).trim().replace("\"", "");
    }
}
