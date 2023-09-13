public abstract class Vehicle {
    private String registrationNumber;
    private int capacity;
    private String id;
    private double weight;
    
    private String destinationPortId;
    
    public String getDestinationPortId() {
        return destinationPortId;
    }
    
    public void setDestinationPortId(String destinationPortId) {
        this.destinationPortId = destinationPortId;
    }

    public Vehicle(String registrationNumber, int capacity, String id, double weight) {
        this.registrationNumber = registrationNumber;
        this.capacity = capacity;
        this.id = id;
        this.weight = weight;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public abstract void loadContainer(Container container);

    public abstract void unloadContainer(Container container);
}