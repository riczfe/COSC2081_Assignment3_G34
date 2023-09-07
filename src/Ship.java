import java.util.ArrayList;
import java.util.List;

public class Ship extends Vehicle {
    private int cargoCapacity;
    private List<Container> containers;

    public Ship(String registrationNumber, int capacity, int cargoCapacity, String id, double weight) {
        super(registrationNumber, capacity, id, weight);
        this.cargoCapacity = cargoCapacity;
        this.containers = new ArrayList<>();
    }

    public int getCargoCapacity() {
        return cargoCapacity;
    }

    public void setCargoCapacity(int cargoCapacity) {
        this.cargoCapacity = cargoCapacity;
    }

    public List<Container> getContainers() {
        return containers;
    }

    public void loadContainer(Container container) {
        if (containers.size() < cargoCapacity) {
            containers.add(container);
            System.out.println("Container loaded onto the ship.");
        } else {
            System.out.println("Ship cargo capacity reached. Cannot load more containers.");
        }
    }

    public void unloadContainer(Container container) {
        if (containers.contains(container)) {
            containers.remove(container);
            System.out.println("Container unloaded from the ship.");
        } else {
            System.out.println("Container not found on the ship.");
        }
    }
}