public class PortManager extends User {
    private Port port;
    private String portId;

    public PortManager(String username, String password, Port port) {
        super(username, password, "portManager");
        this.port = port;
    }

    // Additional methods and logic

    public String getPortId() {
        return portId;
    }

    public void setPortId(String portId) {
        this.portId = portId;
    }

    public void addContainer(Container container) {
        // Add the container to the port
        port.addContainer(container);
    }

    public void removeContainer(Container container) {
        // Remove the container from the port
        port.removeContainer(container);
    }

    public void addVehicle(Vehicle vehicle) {
        // Add the vehicle to the port
        port.addVehicle(vehicle);
    }

    public void removeVehicle(Vehicle vehicle) {
        // Remove the vehicle from the port
        port.removeVehicle(vehicle);
    }
}
