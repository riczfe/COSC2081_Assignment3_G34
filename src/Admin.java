import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.*;

public class Admin extends User {
    private List<PortManager> portManagers;
    private List<Map<String, String>> containersToLoad = new ArrayList<>();

    public Admin(String username, String password) {
        super(username, password, "admin");
        this.portManagers = new ArrayList<>();
    }

// Additional methods and logic

//Add port manager
    public void addPortManager(PortManager portManager) {
        portManagers.add(portManager);

        // Update the account.json file
        updateAccountJsonFile(portManager, true);
    }
    
// REMOVE PORT MANAGER
    public void removePortManager(String username) {
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

            // Remove the specified user data from the JSON string
            jsonString = jsonString.replace(
                    "{\"username\":\"" + username + "\",\"password\":\"manager999\",\"role\":\"portManager\",\"portId\":\"null\"}", "");

            // Write the updated JSON back to the file
            FileWriter fileWriter = new FileWriter(accountJsonFilePath);
            fileWriter.write(jsonString);
            fileWriter.close();

            System.out.println("Port Manager with username " + username + " removed successfully.");
        } catch (IOException e) {
            System.err.println("Error updating account.json file: " + e.getMessage());
        }
    }


//ACCOUNT UPDATE: COMPLETED
    private void updateAccountJsonFile(PortManager portManager, boolean addAccount) {
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

            // If addAccount is true, add the new Port Manager account
            if (addAccount) {
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
                } else {
                    System.err.println("Invalid JSON format in account.json");
                }
            } else {
                // Remove the Port Manager account with the specified username
                jsonString = jsonString.replaceAll(
                    "\\{\\s*\"username\":\"" + portManager.getUsername() + "\",[^}]*\\},?", "");
            }

            // Write the updated JSON back to the file
            FileWriter fileWriter = new FileWriter(accountJsonFilePath);
            fileWriter.write(jsonString);
            fileWriter.close();

        } catch (IOException e) {
            System.err.println("Error updating account.json file: " + e.getMessage());
        }
    }

    
//ADDING VEHICLES
    public void addVehicle() {
        try {
            // Read the existing JSON content from the vehicle.json file
            BufferedReader reader = new BufferedReader(new FileReader("/Users/erictran/eclipse-workspace/COSC2081_Assignment3_G34/src/vehicle.json"));
            StringBuilder jsonContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            String jsonString = jsonContent.toString();

            // Construct the JSON object for the new vehicle
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter vehicle type (truck or ship): ");
            String vehicleType = scanner.nextLine();
            System.out.print("Enter registration number: ");
            String registrationNumber = scanner.nextLine();
            System.out.print("Enter capacity: ");
            int capacity = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            System.out.print("Enter vehicle ID: ");
            String id = scanner.nextLine();
            System.out.print("Enter weight: ");
            double weight = scanner.nextDouble();
            System.out.print("Enter fuel efficiency: ");
            double fuelEfficiency = scanner.nextDouble();
            System.out.print("Enter current fuel: ");
            double currentFuel = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline character
            System.out.print("Enter destination port ID: ");
            String destinationPortId = scanner.nextLine();

            String newVehicle = String.format("{\"type\":\"%s\",\"registrationNumber\":\"%s\",\"capacity\":%d,\"id\":\"%s\",\"weight\":%.2f,\"fuelEfficiency\":%.2f,\"currentFuel\":%.2f,\"destinationPortId\":\"%s\"}",
                    vehicleType, registrationNumber, capacity, id, weight, fuelEfficiency, currentFuel, destinationPortId);

            // Find the position of the last ']' character in the JSON string
            int lastIndex = jsonString.lastIndexOf("]");

            if (lastIndex != -1) {
                // Insert a comma before adding the new vehicle if it's not the first vehicle
                if (lastIndex > 0) {
                    jsonString = jsonString.substring(0, lastIndex) + "," + newVehicle + jsonString.substring(lastIndex);
                } else {
                    jsonString = "[" + newVehicle + "]";
                }
            } else {
                System.err.println("Invalid JSON format in vehicle.json");
            }

            // Write the updated JSON back to the file
            FileWriter fileWriter = new FileWriter("/Users/erictran/eclipse-workspace/COSC2081_Assignment3_G34/src/vehicle.json");
            fileWriter.write(jsonString);
            fileWriter.close();

            System.out.println("Vehicle added successfully.");
        } catch (IOException e) {
            System.err.println("Error adding vehicle: " + e.getMessage());
        }
    }


//REMOVE VEHICLE
    public void removeVehicle(String vehicleId) {
        String vehicleJsonFilePath = "/Users/erictran/eclipse-workspace/COSC2081_Assignment3_G34/src/vehicle.json";

        try {
            // Read the existing JSON content from the vehicle.json file
            BufferedReader reader = new BufferedReader(new FileReader(vehicleJsonFilePath));
            StringBuilder jsonContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            String jsonString = jsonContent.toString();

            // Parse the JSON string into a List of Maps
            List<Map<String, String>> vehicles = new ArrayList<>();
            String[] vehicleEntries = jsonString.split("\\},\\s*\\{");

            for (String entry : vehicleEntries) {
                entry = entry.replaceAll("[{}\"]", "");
                String[] keyValuePairs = entry.split(",");
                Map<String, String> vehicleData = new HashMap<>();

                for (String pair : keyValuePairs) {
                    String[] keyValue = pair.split(":");
                    vehicleData.put(keyValue[0].trim(), keyValue[1].trim());
                }

                vehicles.add(vehicleData);
            }

// Find and remove the vehicle with the specified ID
            boolean removed = false;

            Iterator<Map<String, String>> iterator = vehicles.iterator();
            while (iterator.hasNext()) {
                Map<String, String> vehicle = iterator.next();
                if (vehicle.containsKey("id") && vehicle.get("id").equals(vehicleId)) {
                    iterator.remove();
                    removed = true;
                    break;
                }
            }

            if (removed) {
// Rebuild the JSON string
                StringBuilder updatedJson = new StringBuilder();
                updatedJson.append("[");
                for (Map<String, String> vehicle : vehicles) {
                    if (updatedJson.length() > 1) {
                        updatedJson.append(",");
                    }
                    updatedJson.append("{");
                    for (Map.Entry<String, String> entry : vehicle.entrySet()) {
                        updatedJson.append("\"" + entry.getKey() + "\":\"" + entry.getValue() + "\",");
                    }
                    updatedJson.deleteCharAt(updatedJson.length() - 1); // Remove the trailing comma
                    updatedJson.append("}");
                }
                updatedJson.append("]");

                // Write the updated JSON back to the file
                FileWriter fileWriter = new FileWriter(vehicleJsonFilePath);
                fileWriter.write(updatedJson.toString());
                fileWriter.close();

                System.out.println("Vehicle with ID " + vehicleId + " and all related data removed successfully.");
            } else {
                System.out.println("Vehicle with ID " + vehicleId + " not found.");
            }
        } catch (IOException e) {
            System.err.println("Error removing vehicle: " + e.getMessage());
        }
    }



// Add port manually
    public void addPort(Port port) {
        String portJsonFilePath = "/Users/erictran/eclipse-workspace/COSC2081_Assignment3_G34/src/port.json";

        try {
            // Read the existing JSON content
            BufferedReader reader = new BufferedReader(new FileReader(portJsonFilePath));
            StringBuilder jsonContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            String jsonString = jsonContent.toString();

            // Construct the JSON object for the new port, including fuelConsumptionPerKm
            String newPort = String.format(
                "{\"id\":\"%s\",\"name\":\"%s\",\"latitude\":%.4f,\"longitude\":%.4f,\"storingCapacity\":%d,\"landingAbility\":%b,\"containerCount\":%d,\"vehicleCount\":%d,\"fuelConsumptionPerKm\":{\"Ship\":%.2f,\"Truck\":%.2f}}",
                port.getId(), port.getName(), port.getLatitude(), port.getLongitude(), port.getStoringCapacity(),
                port.isLandingAbility(), port.getContainerCount(), port.getVehicleCount(),
                port.getFuelConsumption(), port.getFuelConsumption());

            // Check if the JSON array is empty
            boolean isEmpty = jsonString.trim().equals("[]");

            if (isEmpty) {
                jsonString = "[" + newPort + "]";
            } else {
                jsonString = jsonString.substring(0, jsonString.length() - 1) + "," + newPort + "]";
            }

            // Write the updated JSON back to the file
            FileWriter fileWriter = new FileWriter(portJsonFilePath);
            fileWriter.write(jsonString);
            fileWriter.close();

            System.out.println("Port added successfully.");
        } catch (IOException e) {
            System.err.println("Error adding port: " + e.getMessage());
        }
    }


// Remove port manually
    public void removePort(String portId) {
        String portJsonFilePath = "/Users/erictran/eclipse-workspace/COSC2081_Assignment3_G34/src/port.json";

        try {
            // Read the existing JSON content
            BufferedReader reader = new BufferedReader(new FileReader(portJsonFilePath));
            StringBuilder jsonContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            String jsonString = jsonContent.toString();

            // Find the index of the port data in the JSON string
            int startIndex = jsonString.indexOf("{\"id\":\"" + portId + "\"");
            int endIndex = jsonString.indexOf("}", startIndex) + 1;

            // Check if the port data was found
            if (startIndex != -1 && endIndex != 0) {
                // Remove the port data from the JSON string
                jsonString = jsonString.substring(0, startIndex) + jsonString.substring(endIndex);

                // Write the updated JSON back to the file
                FileWriter fileWriter = new FileWriter(portJsonFilePath);
                fileWriter.write(jsonString);
                fileWriter.close();

                System.out.println("Port with ID " + portId + " removed successfully.");
            } else {
                System.out.println("Port with ID " + portId + " not found.");
            }
        } catch (IOException e) {
            System.err.println("Error removing port: " + e.getMessage());
        }
    }

 // Add Container
    public void addContainer() {
        try {
            // Read the existing JSON content from the container.json file
            BufferedReader reader = new BufferedReader(new FileReader("/Users/erictran/eclipse-workspace/COSC2081_Assignment3_G34/src/container.json"));
            StringBuilder jsonContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            String jsonString = jsonContent.toString();

            // Construct the JSON array for the new container
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter container type (Dry storage, Open top, Open side, Refrigerated, Liquid): ");
            String type = scanner.nextLine();
            System.out.print("Enter container weight (in kg): ");
            int weight = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            // Generate a unique ID for the container (you can use a more sophisticated method)
            String id = generateUniqueContainerId(jsonString);

            // Look up the fuel consumption values based on the container type
            double shipFuelConsumption = 0.0;
            double truckFuelConsumption = 0.0;

            if ("Dry storage".equals(type)) {
                shipFuelConsumption = 3.5;
                truckFuelConsumption = 4.6;
            } else if ("Open top".equals(type)) {
                shipFuelConsumption = 2.8;
                truckFuelConsumption = 3.2;
            } else if ("Open side".equals(type)) {
                shipFuelConsumption = 2.7;
                truckFuelConsumption = 3.2;
            } else if ("Refrigerated".equals(type)) {
                shipFuelConsumption = 4.5;
                truckFuelConsumption = 5.4;
            } else if ("Liquid".equals(type)) {
                shipFuelConsumption = 4.8;
                truckFuelConsumption = 5.3;
            }

            // Calculate fuel consumption based on weight (convert weight to metric tons)
            double shipConsumption = (weight / 1000.0) * shipFuelConsumption;
            double truckConsumption = (weight / 1000.0) * truckFuelConsumption;

            // Construct the JSON object for the new container
            String newContainer = String.format(
                "{\"id\":\"%s\",\"type\":\"%s\",\"weight\":%d,\"fuelConsumption\":{\"Ship\":%.1f,\"Truck\":%.1f}}",
                id, type, weight, shipConsumption, truckConsumption
            );

            // Find the position of the last ']' character in the JSON string
            int lastIndex = jsonString.lastIndexOf("]");

            if (lastIndex != -1) {
                // Insert a comma before adding the new container if it's not the first container
                if (lastIndex > 0) {
                    jsonString = jsonString.substring(0, lastIndex) + "," + newContainer + jsonString.substring(lastIndex);
                } else {
                    jsonString = "[" + newContainer + "]";
                }
            } else {
                System.err.println("Invalid JSON format in container.json");
            }

            // Write the updated JSON back to the file
            FileWriter fileWriter = new FileWriter("/Users/erictran/eclipse-workspace/COSC2081_Assignment3_G34/src/container.json");
            fileWriter.write(jsonString);
            fileWriter.close();

            System.out.println("Container added successfully.");
        } catch (IOException e) {
            System.err.println("Error adding container: " + e.getMessage());
        }
    }


 // Remove Container
    public void removeContainer(String containerIdToRemove) {
        try {
            String jsonFilePath = "/Users/erictran/eclipse-workspace/COSC2081_Assignment3_G34/src/container.json";

            // Read the JSON data from the file into a StringBuilder
            BufferedReader reader = new BufferedReader(new FileReader(jsonFilePath));
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
            reader.close();

            String jsonString = jsonContent.toString();

            // Find the position of the last ']' character in the JSON string
            int lastIndex = jsonString.lastIndexOf("]");

            if (lastIndex != -1) {
                // Remove the trailing ']' to work with the JSON array
                jsonString = jsonString.substring(0, lastIndex);

                // Split the JSON string into individual containers
                String[] containerStrings = jsonString.split("\\},\\s*\\{");

                // Find the container with the specified ID
                int indexToRemove = -1;
                for (int i = 0; i < containerStrings.length; i++) {
                    if (containerStrings[i].contains("\"id\":\"" + containerIdToRemove + "\"")) {
                        indexToRemove = i;
                        break;
                    }
                }

                if (indexToRemove != -1) {
                    // Remove the container from the array of containers
                    ArrayList<String> updatedContainers = new ArrayList<>(Arrays.asList(containerStrings));
                    updatedContainers.remove(indexToRemove);

                    // Join the containers back into a JSON string
                    String updatedJson = String.join("},{", updatedContainers);

                    // Add square brackets to make it a valid JSON array
                    updatedJson = "[" + updatedJson + "]";

                    // Write the updated JSON back to the file
                    FileWriter fileWriter = new FileWriter(jsonFilePath);
                    fileWriter.write(updatedJson);
                    fileWriter.close();

                    System.out.println("Container with ID " + containerIdToRemove + " removed successfully.");
                } else {
                    System.out.println("Container with ID " + containerIdToRemove + " not found.");
                }
            } else {
                System.err.println("Invalid JSON format in container.json");
            }
        } catch (IOException e) {
            System.err.println("Error removing container: " + e.getMessage());
        }
    }



// Helper method to parse the JSON array into a list of container objects
    private List<String> parseContainerJson(String jsonString) {
        List<String> containers = new ArrayList<>();
        int startIndex = jsonString.indexOf("[");
        int endIndex = jsonString.lastIndexOf("]");
        if (startIndex != -1 && endIndex != -1) {
            String containerArray = jsonString.substring(startIndex + 1, endIndex);
            String[] containerStrings = containerArray.split(",");
            for (String containerString : containerStrings) {
                containers.add(containerString.trim());
            }
        }
        return containers;
    }
 
//GENERATE CONTAINER id
	    private String generateUniqueContainerId(String jsonString) {
	        // Implement a method to generate a unique container ID
	        // For example, you can find the highest existing ID and increment it
	        // Here, we'll use a simple approach by counting the existing containers
	        List<String> containers = parseContainerJson(jsonString);
	        int containerCount = containers.size();
	        return "c-" + String.format("%03d", containerCount + 1);
	    }


    // Helper method to update the vehicle.json file
    private void updateVehicleJsonFile(Vehicle vehicle, String operation) {
        String vehicleJsonFilePath = "/Users/erictran/eclipse-workspace/COSC2081_Assignment3_G34/src/vehicle.json";

        try {
            // Read the existing JSON content
            BufferedReader reader = new BufferedReader(new FileReader(vehicleJsonFilePath));
            StringBuilder jsonContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            String jsonString = jsonContent.toString();

            // Modify the JSON string based on the operation (add/remove) and the vehicle data
            if (operation.equals("add")) {
                // Construct the JSON object for the new vehicle and append it to the array
                String newVehicle = String.format("{\"id\":\"%s\",\"name\":\"%s\",\"weight\":%f}",
                        vehicle.getId(), vehicle.getWeight());

                // Find the position of the last ']' character in the JSON string
                int lastIndex = jsonString.lastIndexOf("]");

                if (lastIndex != -1) {
                    // Insert a comma before adding the new vehicle if it's not the first vehicle
                    if (lastIndex > 0) {
                        jsonString = jsonString.substring(0, lastIndex) + "," + newVehicle + jsonString.substring(lastIndex);
                    } else {
                        jsonString = "[" + newVehicle + "]";
                    }
                } else {
                    System.err.println("Invalid JSON format in vehicle.json");
                }
            } else if (operation.equals("remove")) {
                // Implement logic to remove the vehicle based on its ID, if it exists
                // You may need to parse the JSON and manipulate the data accordingly
                // For example, find the vehicle by ID and remove it from the array
                // You can use a library like Gson for easier JSON manipulation.
            }

            // Write the updated JSON back to the file
            FileWriter fileWriter = new FileWriter(vehicleJsonFilePath);
            fileWriter.write(jsonString);
            fileWriter.close();

        } catch (IOException e) {
            System.err.println("Error updating vehicle.json file: " + e.getMessage());
        }
    }

    // Helper method to update the account.json file
    private void updatePortJsonFile(Port port, String operation) {
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

            // Modify the JSON string based on the operation (add/remove) and the port data
            if (operation.equals("add")) {
                // Construct the JSON object for the new port and append it to the array
                String newPort = String.format("{\"id\":\"%s\",\"name\":\"%s\",\"latitude\":%f,\"longitude\":%f,\"storingCapacity\":%d,\"landingAbility\":%b,\"containerCount\":%d,\"vehicleCount\":%d,\"fuelConsumption\":%f}",
                        port.getId(), port.getName(), port.getLatitude(), port.getLongitude(), port.getStoringCapacity(),
                        port.isLandingAbility(), port.getContainerCount(), port.getVehicleCount(), port.getFuelConsumption());

                // Find the position of the last ']' character in the JSON string
                int lastIndex = jsonString.lastIndexOf("]");

                if (lastIndex != -1) {
                    // Insert a comma before adding the new port if it's not the first port
                    if (lastIndex > 0) {
                        jsonString = jsonString.substring(0, lastIndex) + "," + newPort + jsonString.substring(lastIndex);
                    } else {
                        jsonString = "[" + newPort + "]";
                    }
                } else {
                    System.err.println("Invalid JSON format in account.json");
                }
            } else if (operation.equals("remove")) {
                // Implement logic to remove the port based on its ID, if it exists
                // You may need to parse the JSON and manipulate the data accordingly
                // For example, find the port by ID and remove it from the array
                // You can use a library like Gson for easier JSON manipulation.
            }

            // Write the updated JSON back to the file
            FileWriter fileWriter = new FileWriter(accountJsonFilePath);
            fileWriter.write(jsonString);
            fileWriter.close();

        } catch (IOException e) {
            System.err.println("Error updating account.json file: " + e.getMessage());
        }
    }

    // Helper method to update the vehicle.json file for Containers
    private void updateContainerJsonFile(Container container, String operation) {
        String vehicleJsonFilePath = "/Users/erictran/eclipse-workspace/COSC2081_Assignment3_G34/src/vehicle.json";

        try {
            // Read the existing JSON content
            BufferedReader reader = new BufferedReader(new FileReader(vehicleJsonFilePath));
            StringBuilder jsonContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            String jsonString = jsonContent.toString();

            // Modify the JSON string based on the operation (add/remove) and the container data
            if (operation.equals("add")) {
                // Construct the JSON object for the new container and append it to the array
                String newContainer = String.format("{\"id\":\"%s\",\"name\":\"%s\",\"weight\":%f}",
                        container.getId(), container.getWeight());

                // Find the position of the last ']' character in the JSON string
                int lastIndex = jsonString.lastIndexOf("]");

                if (lastIndex != -1) {
                    // Insert a comma before adding the new container if it's not the first container
                    if (lastIndex > 0) {
                        jsonString = jsonString.substring(0, lastIndex) + "," + newContainer + jsonString.substring(lastIndex);
                    } else {
                        jsonString = "[" + newContainer + "]";
                    }
                } else {
                    System.err.println("Invalid JSON format in vehicle.json");
                }
            } else if (operation.equals("remove")) {
                // Implement logic to remove the container based on its ID, if it exists
                // You may need to parse the JSON and manipulate the data accordingly
                // For example, find the container by ID and remove it from the array
                // You can use a library like Gson for easier JSON manipulation.
            }

            // Write the updated JSON back to the file
            FileWriter fileWriter = new FileWriter(vehicleJsonFilePath);
            fileWriter.write(jsonString);
            fileWriter.close();

        } catch (IOException e) {
            System.err.println("Error updating vehicle.json file: " + e.getMessage());
        }
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

    
//LOAD OR UNLOAD CONTAINERS
    // Load Containers onto a Vehicle
    public void loadContainersToVehicle(String vehicleId, List<Container> containersToLoad) {
        try {
            String vehicleJsonFilePath = "/Users/erictran/eclipse-workspace/COSC2081_Assignment3_G34/src/vehicle.json";
            String movingVehicleJsonFilePath = "/Users/erictran/eclipse-workspace/COSC2081_Assignment3_G34/src/movingVehicle.json";

            String vehicleJson = readJsonFile(vehicleJsonFilePath);
            String movingVehicleJson = readJsonFile(movingVehicleJsonFilePath);

            List<Map<String, String>> vehicles = parseJsonArray(vehicleJson);
            List<Map<String, String>> movingVehicles = parseJsonArray(movingVehicleJson);

            Map<String, String> selectedVehicle = null;
            for (Map<String, String> vehicle : vehicles) {
                if (vehicle.containsKey("id") && vehicle.get("id").equals(vehicleId)) {
                    selectedVehicle = vehicle;
                    break;
                }
            }

            if (selectedVehicle != null) {
                int maxContainers = 5;
                int currentContainers = movingVehicles.size();
                int containersToAdd = Math.min(maxContainers - currentContainers, containersToLoad.size());

                if (containersToAdd > 0) {
                    List<Container> loadedContainers = containersToLoad.subList(0, containersToAdd);
                    movingVehicles.addAll(serializeContainersToMap(selectedVehicle.get("id"), loadedContainers));

                    containersToLoad.subList(0, containersToAdd).clear();

                    double shipFuelConsumption = Double.parseDouble(selectedVehicle.get("fuelEfficiency"));
                    double loadedContainersWeight = loadedContainers.stream()
                            .mapToDouble(container -> container.getWeight() / 1000.0)
                            .sum();
                    double fuelConsumption = loadedContainersWeight * shipFuelConsumption;
                    double currentFuel = Double.parseDouble(selectedVehicle.get("currentFuel"));
                    selectedVehicle.put("currentFuel", String.valueOf(currentFuel - fuelConsumption));

                    updateVehicleJson(vehicles, vehicleJsonFilePath);
                    updateMovingVehicleJson(movingVehicles, movingVehicleJsonFilePath);

                    System.out.println("Containers loaded onto the vehicle successfully.");
                } else {
                    System.out.println("The vehicle cannot accommodate more containers.");
                }
            } else {
                System.out.println("Vehicle with ID " + vehicleId + " not found.");
            }
        } catch (IOException e) {
            System.err.println("Error loading containers onto the vehicle: " + e.getMessage());
        }
    }

    // Unload Containers from a Vehicle
    public void unloadContainersFromVehicle(String vehicleId, List<Container> containersToUnload) {
        try {
            String vehicleJsonFilePath = "/Users/erictran/eclipse-workspace/COSC2081_Assignment3_G34/src/vehicle.json";
            String movingVehicleJsonFilePath = "/Users/erictran/eclipse-workspace/COSC2081_Assignment3_G34/src/movingVehicle.json";

            String vehicleJson = readJsonFile(vehicleJsonFilePath);
            String movingVehicleJson = readJsonFile(movingVehicleJsonFilePath);

            List<Map<String, String>> vehicles = parseJsonArray(vehicleJson);
            List<Map<String, String>> movingVehicles = parseJsonArray(movingVehicleJson);

            Map<String, String> selectedVehicle = null;
            for (Map<String, String> vehicle : vehicles) {
                if (vehicle.containsKey("id") && vehicle.get("id").equals(vehicleId)) {
                    selectedVehicle = vehicle;
                    break;
                }
            }

            if (selectedVehicle != null) {
                List<Map<String, String>> containersToUnloadMaps = serializeContainersToMap(vehicleId, containersToUnload);
                movingVehicles.removeAll(containersToUnloadMaps);

                updateVehicleJson(vehicles, vehicleJsonFilePath);
                updateMovingVehicleJson(movingVehicles, movingVehicleJsonFilePath);

                System.out.println("Containers unloaded from the vehicle successfully.");
            } else {
                System.out.println("Vehicle with ID " + vehicleId + " not found.");
            }
        } catch (IOException e) {
            System.err.println("Error unloading containers from the vehicle: " + e.getMessage());
        }
    }

    // Check if a Vehicle Can Move
    
    
    // Helper method to get the count of containers loaded onto a vehicle
    private int getLoadedContainerCount(String vehicleId, List<Map<String, String>> movingVehicles) {
        int count = 0;
        for (Map<String, String> movingVehicle : movingVehicles) {
            if (movingVehicle.containsKey("vehicleId") && movingVehicle.get("vehicleId").equals(vehicleId)) {
                count++;
            }
        }
        return count;
    }
    
    public boolean canVehicleMove(String vehicleId, String destinationPortId) {
        try {
            String vehicleJsonFilePath = "/Users/erictran/eclipse-workspace/COSC2081_Assignment3_G34/src/vehicle.json";
            String portJsonFilePath = "/Users/erictran/eclipse-workspace/COSC2081_Assignment3_G34/src/port.json";
            String movingVehicleJsonFilePath = "/Users/erictran/eclipse-workspace/COSC2081_Assignment3_G34/src/movingVehicle.json";

            String vehicleJson = readJsonFile(vehicleJsonFilePath);
            String portJson = readJsonFile(portJsonFilePath);
            String movingVehicleJson = readJsonFile(movingVehicleJsonFilePath);

            List<Map<String, String>> vehicles = parseJsonArray(vehicleJson);
            List<Map<String, String>> ports = parseJsonArray(portJson);
            List<Map<String, String>> movingVehicles = parseJsonArray(movingVehicleJson);

            Map<String, String> selectedVehicle = null;
            for (Map<String, String> vehicle : vehicles) {
                if (vehicle.containsKey("id") && vehicle.get("id").equals(vehicleId)) {
                    selectedVehicle = vehicle;
                    break;
                }
            }

            Map<String, String> destinationPort = null;
            for (Map<String, String> port : ports) {
                if (port.containsKey("id") && port.get("id").equals(destinationPortId)) {
                    destinationPort = port;
                    break;
                }
            }

            if (selectedVehicle != null && destinationPort != null) {
                int currentContainers = getLoadedContainerCount(selectedVehicle.get("id"), movingVehicles);
                int maxContainers = 5;

                return currentContainers + containersToLoad.size() <= maxContainers;
            } else {
                System.out.println("Vehicle or destination port not found.");
            }
        } catch (IOException e) {
            System.err.println("Error checking if the vehicle can move: " + e.getMessage());
        }

        return false;
    }


    // Store Container Loading in movingVehicle.json
    public void storeContainerLoading(String vehicleId, List<Container> containersToLoad) {
        try {
            String movingVehicleJsonFilePath = "/Users/erictran/eclipse-workspace/COSC2081_Assignment3_G34/src/movingVehicle.json";
            String movingVehicleJson = readJsonFile(movingVehicleJsonFilePath);
            List<Map<String, String>> movingVehicles = parseJsonArray(movingVehicleJson);

            movingVehicles.addAll(serializeContainersToMap(vehicleId, containersToLoad));

            updateMovingVehicleJson(movingVehicles, movingVehicleJsonFilePath);

            System.out.println("Container loading recorded successfully.");
        } catch (IOException e) {
            System.err.println("Error recording container loading: " + e.getMessage());
        }
    }

    // Store Container Unloading in movingVehicle.json
    public void storeContainerUnloading(String vehicleId, List<Container> containersToUnload) {
        try {
            String movingVehicleJsonFilePath = "/Users/erictran/eclipse-workspace/COSC2081_Assignment3_G34/src/movingVehicle.json";
            String movingVehicleJson = readJsonFile(movingVehicleJsonFilePath);
            List<Map<String, String>> movingVehicles = parseJsonArray(movingVehicleJson);

            List<Map<String, String>> containersToUnloadMaps = serializeContainersToMap(vehicleId, containersToUnload);
            movingVehicles.removeAll(containersToUnloadMaps);

            updateMovingVehicleJson(movingVehicles, movingVehicleJsonFilePath);

            System.out.println("Container unloading recorded successfully.");
        } catch (IOException e) {
            System.err.println("Error recording container unloading: " + e.getMessage());
        }
    }

    // Other methods...

    // Helper method to read JSON content from a file
    private String readJsonFile(String filePath) throws IOException {
        StringBuilder jsonContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
        }
        return jsonContent.toString();
    }

    // Helper method to parse a JSON array into a list of maps
    private List<Map<String, String>> parseJsonArray(String jsonArray) {
        // Implement JSON parsing logic here and return the list of maps.
        // Example:
        /*
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Map<String, String>>>() {}.getType();
        return gson.fromJson(jsonArray, listType);
        */

        // Replace the return statement with the actual implementation
        return new ArrayList<>();
    }

    // Helper method to serialize containers to Map objects
    private List<Map<String, String>> serializeContainersToMap(String vehicleId, List<Container> containers) {
        List<Map<String, String>> containerMaps = new ArrayList<>();

        for (Container container : containers) {
            Map<String, String> containerMap = new HashMap<>();
            containerMap.put("vehicleId", vehicleId);
            containerMap.put("containerId", container.getId());
            containerMaps.add(containerMap);
        }

        return containerMaps;
    }

    // Helper method to update vehicle.json file
    private void updateVehicleJson(List<Map<String, String>> vehicles, String filePath) throws IOException {
        // Implement logic to update the vehicle.json file with the modified list of vehicles
        // Convert the list of vehicles back to JSON and write it to the file.
    }

    // Helper method to update movingVehicle.json file
    private void updateMovingVehicleJson(List<Map<String, String>> movingVehicles, String filePath) throws IOException {
        // Implement logic to update the movingVehicle.json file with the modified list of loaded containers
        // Convert the list of loaded containers back to JSON and write it to the file.
    }

//OVERRIDES LOGIN 
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