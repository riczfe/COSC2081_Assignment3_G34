import java.util.ArrayList;
import java.util.List;

public class Truck extends Vehicle {
    private int loadingCapacity;
    private List<Container> containers;

    public Truck(String registrationNumber, int capacity, int loadingCapacity, String id, double weight) {
        super(registrationNumber, capacity, id, weight);
        this.loadingCapacity = loadingCapacity;
        this.containers = new ArrayList<>();
    }


    public int getLoadingCapacity() {
        return loadingCapacity;
    }

    public void setLoadingCapacity(int loadingCapacity) {
        this.loadingCapacity = loadingCapacity;
    }

    public List<Container> getContainers() {
        return containers;
    }

    public void loadContainer(Container container) {
        if (containers.size() < loadingCapacity) {
            containers.add(container);
            setWeight(getWeight() + container.getWeight());
            System.out.println("Container loaded onto the truck.");
        } else {
            System.out.println("Truck loading capacity reached. Cannot load more containers.");
        }
    }

    public void unloadContainer(Container container) {
        if (containers.contains(container)) {
            containers.remove(container);
            setWeight(getWeight() - container.getWeight());
            System.out.println("Container unloaded from the truck.");
        } else {

            System.out.println("Container not found on the truck.");
        }
    }
}