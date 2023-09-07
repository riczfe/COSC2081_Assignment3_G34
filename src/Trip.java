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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getSourcePortId() {
        return sourcePortId;
    }

    public void setSourcePortId(String sourcePortId) {
        this.sourcePortId = sourcePortId;
    }

    public String getDestinationPortId() {
        return destinationPortId;
    }

    public void setDestinationPortId(String destinationPortId) {
        this.destinationPortId = destinationPortId;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Action: " + action +
                ", Source Port ID: " + sourcePortId +
                ", Destination Port ID: " + destinationPortId +
                ", Distance: " + distance +
                ", Duration: " + duration;
    }
}