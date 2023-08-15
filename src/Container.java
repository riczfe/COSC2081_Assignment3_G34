public class Container {
    private String ID;
    private double weight;
    private String type;

    public Container(String ID, double weight, String type) {
        this.ID = ID;
        this.weight = weight;
        this.type = type;
    }

    public double getFuelConsumption(Vehicle vehicle) {
		return weight;
        // Get the fuel consumption for the container based on the vehicle type
        // Implementation not shown
    }
}