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

    public Port(String id, String name, double latitude, double longitude, int storingCapacity, boolean landingAbility) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storingCapacity = storingCapacity;
        this.landingAbility = landingAbility;
        this.containerCount = 0;
        this.vehicleCount = 0;
        this.trafficHistory = new ArrayList<>();
        this.containerList = new ArrayList<>();
        this.vehicleList = new ArrayList<>();
    }

    // Getters and setters
    // Getters
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

    public int getContainerCount() {
        return containerCount;
    }

    public int getVehicleCount() {
        return vehicleCount;
    }

    public List<Trip> getTrafficHistory() {
        return trafficHistory;
    }

    public List<Container> getContainerList() {
        return containerList;
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    // Setters
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

    public void setContainerCount(int containerCount) {
        this.containerCount = containerCount;
    }

    public void setVehicleCount(int vehicleCount) {
        this.vehicleCount = vehicleCount;
    }

    public void setTrafficHistory(List<Trip> trafficHistory) {
        this.trafficHistory = trafficHistory;
    }

    public void setContainerList(List<Container> containerList) {
        this.containerList = containerList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    
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
}