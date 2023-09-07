public abstract class Vehicle extends Container {
    private String registrationNumber;
    private int capacity;

    public Vehicle(String registrationNumber, int capacity, String id, double weight) {
        super(id, weight);
        this.registrationNumber = registrationNumber;
        this.capacity = capacity;
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

    public abstract void loadContainer(Container container);

    public abstract void unloadContainer(Container container);

}