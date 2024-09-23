package scripts;

public class Route {
    private City source;
    private City destination;
    private double cost;
    private double time;
    private String transportType;
    public Route(City source, City destination, double cost, double time, String transportType) {
        this.source = source;
        this.destination = destination;
        this.cost = cost;
        this.time = time;
        this.transportType = transportType;
    }

    public City getSource() {
        return source;
    }

    public City getDestination() {
        return destination;
    }

    public double getCost() {
        return cost;
    }

    public double getTime() {
        return time;
    }

    public String getTransportType() {
        return transportType;  // Getter for transport type
    }
}
