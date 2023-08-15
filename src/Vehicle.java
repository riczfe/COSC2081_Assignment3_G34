public class Vehicle {
    private String ID;
    private String name;
    private double currentFuel;
    private double carryingCapacity;
    private double fuelCapacity;
    private Port currentPort;

    public Vehicle(String ID, String name, double currentFuel, double carryingCapacity, double fuelCapacity) {
        this.ID = ID;
        this.name = name;
        this.currentFuel = currentFuel;
        this.carryingCapacity = carryingCapacity;
        this.fuelCapacity = fuelCapacity;
        this.currentPort = null;
    }

    public void loadContainer(Container container) {
        // Load the container onto the vehicle
        // Implementation not shown
    }

    public void unloadContainer(Container container) {
        // Unload the container from the vehicle
        // Implementation not shown
    }

    public boolean canMoveToPort(Port port) {
        // Check if the vehicle can successfully move to the given port with its current load
        // Implementation not shown
    }

    public void moveToPort(Port port) {
        // Move the vehicle to the given port
        // Implementation not shown
    }

    public void refuel() {
        // Refuel the vehicle
        // Implementation not shown
    }
}