public class Trip {
    private String action;
    private String sourcePortId;
    private String destinationPortId;
    private double distance;
    private double duration;

    public Trip(String action, String sourcePortId, String destinationPortId, double distance, double duration) {
        this.action = action;
        this.sourcePortId = sourcePortId;
        this.destinationPortId = destinationPortId;
        this.distance = distance;
        this.duration = duration;
    }

    // Getters and setters

    @Override
    public String toString() {
        return "Action: " + action +
                ", Source Port ID: " + sourcePortId +
                ", Destination Port ID: " + destinationPortId +
                ", Distance: " + distance +
                ", Duration: " + duration;
    }
}