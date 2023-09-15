import java.util.Scanner;

public class PortManagerSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Port port = initializePort(); // Initialize the port with data from JSON

        System.out.println("Welcome to the Port Management System!");
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        User user = new User(username, password);
        if (user.login()) {
            if (user instanceof Admin) {
                Admin admin = (Admin) user;
                displayAdminMenu(admin, port, scanner);
            } else if (user instanceof PortManager) {
                PortManager portManager = (PortManager) user;
                displayPortManagerMenu(portManager, port, scanner);
            }
        } else {
            System.out.println("Login failed. Exiting.");
        }

        scanner.close();
    }

    private static Port initializePort() {
        // Read port data from JSON and create a Port object
        // You can use the Jackson library or a similar approach as in your previous code
        // Initialize and return a Port object with the read data
        return null; // Replace with actual initialization logic
    }

    private static void displayAdminMenu(Admin admin, Port port, Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add Port Manager");
            System.out.println("2. Remove Port Manager");
            System.out.println("3. Perform Statistics Operations");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    // Implement logic to add a port manager
                    break;
                case 2:
                    // Implement logic to remove a port manager
                    break;
                case 3:
                    admin.performStatisticsOperations(port);
                    break;
                case 4:
                    exit = true;
                    System.out.println("Exiting.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void displayPortManagerMenu(PortManager portManager, Port port, Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nPort Manager Menu:");
            System.out.println("1. Add Container");
            System.out.println("2. Remove Container");
            System.out.println("3. Add Vehicle");
            System.out.println("4. Remove Vehicle");
            System.out.println("5. Exit");
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
                    exit = true;
                    System.out.println("Exiting.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}
