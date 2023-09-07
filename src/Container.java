public class Container {
    private String id;
    private double weight;
    private String destinationPortId;

    public Container(String id, double weight) {
        this.id = id;
        this.weight = weight;
    }

    public String getDestinationPortId() {
        return destinationPortId;
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
    
    public void setDestinationPortId(String destinationPortId) {
        this.destinationPortId = destinationPortId;
    }
}