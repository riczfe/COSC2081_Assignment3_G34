import java.util.ArrayList;
import java.util.List;

public class Port {
    private String id;
    private String name;
    private double latitude;
    private double longitude;
    private int storingCapacity;
    private boolean landingAbility;
    private int containerCount;
    private int vehicleCount;
    private List<Trip> trafficHistory;
    private List<Container> containerList;
    private List<Vehicle> vehicleList;
    private double fuelConsumption;

    public Port(String id, String name, double latitude, double longitude, int storingCapacity, boolean landingAbility,
            int containerCount, int vehicleCount, double fuelConsumption) {
    this.id = id;
    this.name = name;
    this.latitude = latitude;
    this.longitude = longitude;
    this.storingCapacity = storingCapacity;
    this.landingAbility = landingAbility;
    this.containerCount = containerCount;
    this.vehicleCount = vehicleCount;
    this.trafficHistory = new ArrayList<>();
    this.containerList = new ArrayList<>();
    this.vehicleList = new ArrayList<>();
    this.fuelConsumption = fuelConsumption;
}

    // Getters for id, name, latitude, longitude, storingCapacity, landingAbility
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getStoringCapacity() {
        return storingCapacity;
    }

    public boolean isLandingAbility() {
        return landingAbility;
    }

    // Setters for id, name, latitude, longitude, storingCapacity, landingAbility
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setStoringCapacity(int storingCapacity) {
        this.storingCapacity = storingCapacity;
    }

    public void setLandingAbility(boolean landingAbility) {
        this.landingAbility = landingAbility;
    }

    // Getters for containerCount, vehicleCount
    public int getContainerCount() {
        return containerCount;
    }

    public int getVehicleCount() {
        return vehicleCount;
    }

    // Setters for containerCount, vehicleCount
    public void setContainerCount(int containerCount) {
        this.containerCount = containerCount;
    }

    public void setVehicleCount(int vehicleCount) {
        this.vehicleCount = vehicleCount;
    }

    // Getters for trafficHistory, containerList, vehicleList
    public List<Trip> getTrafficHistory() {
        return trafficHistory;
    }

    public List<Container> getContainerList() {
        return containerList;
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    // Setters for trafficHistory, containerList, vehicleList
    public void setTrafficHistory(List<Trip> trafficHistory) {
        this.trafficHistory = trafficHistory;
    }

    public void setContainerList(List<Container> containerList) {
        this.containerList = containerList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    // Getters and Setters for fuelConsumption
    public double getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    // Method to calculate distance between two ports
    public double calculateDistance(Port otherPort) {
        // Calculate the distance between this port and the other port using their latitude and longitude
        double latDiff = Math.toRadians(otherPort.latitude - this.latitude);
        double lonDiff = Math.toRadians(otherPort.longitude - this.longitude);
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2)
                + Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(otherPort.latitude))
                * Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = 6371 * c; // Earth's radius in kilometers
        return distance;
    }

    public void addContainer(Container container) {
        if (containerCount < storingCapacity) {
            containerCount++;
            containerList.add(container);
            Trip trip = new Trip("Container Added", this.id, container.getDestinationPortId(), 0, 0);
            trafficHistory.add(trip);
        } else {
            System.out.println("Storing capacity exceeded. Cannot add container.");
        }
    }

    public void removeContainer(Container container) {
        if (containerCount > 0) {
            containerCount--;
            containerList.remove(container);
            Trip trip = new Trip("Container Removed", this.id, container.getDestinationPortId(), 0, 0);
            trafficHistory.add(trip);
        } else {
            System.out.println("No containers available to remove.");
        }
    }

    public void addVehicle(Vehicle vehicle) {
        vehicleCount++;
        vehicleList.add(vehicle);
        Trip trip = new Trip("Vehicle Added", this.id, vehicle.getDestinationPortId(), 0, 0);
        trafficHistory.add(trip);
    }

    public void removeVehicle(Vehicle vehicle) {
        if (vehicleCount > 0) {
            vehicleCount--;
            vehicleList.remove(vehicle);
            Trip trip = new Trip("Vehicle Removed", this.id, vehicle.getDestinationPortId(), 0, 0);
            trafficHistory.add(trip);
        } else {
            System.out.println("No vehicles available to remove.");
        }
    }

    public void printTrafficHistory() {
        for (Trip trip : trafficHistory) {
            System.out.println(trip.toString());
        }
    }
    
    public boolean canMoveToPort(Vehicle vehicle) {
        // Implement logic to determine if a vehicle can successfully move to this port with its current load
        // For example, you can check if the port has landing ability and if the vehicle's weight is within the port's capacity
        return this.isLandingAbility() && vehicle.getWeight() <= this.getStoringCapacity();
    }

    public void refuelVehicle(Vehicle vehicle) {
        // Implement logic to refuel a vehicle
        // For example, you can set the vehicle's weight to its maximum capacity
        vehicle.setWeight(vehicle.getCapacity());
    }
    
    //Method for fuelConsumption
    public void calculateFuelConsumption(Vehicle vehicle, double distance) {
        // Calculate the fuel consumption for the given vehicle and distance
        double fuelConsumed = (distance / vehicle.getFuelEfficiency()) * vehicle.getCurrentFuel();
        fuelConsumption += fuelConsumed;
    }
    public double calculateTripFuelConsumption(Vehicle vehicle, double distance) {
        // Calculate the fuel consumption for the given vehicle and distance
        return distance* vehicle.getWeight() * 3.0;
    }
    public void updateFuelConsumption(Vehicle vehicle, double additionalFuelConsumed) {
        // Update the fuel consumption for the given vehicle with the additional fuel consumed
        fuelConsumption += additionalFuelConsumed;
    }

}