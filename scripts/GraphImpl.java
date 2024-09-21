package scripts;
import java.util.*;
public class GraphImpl implements Graph {
    private Map<City, List<Route>> adjList = new HashMap<>();
    @Override
    public void addCity(City city) {
        adjList.putIfAbsent(city, new ArrayList<>());
    }
    @Override
    public void addRoute(Route route) {
        // Ensure the source and destination cities are present in the graph
        if (!adjList.containsKey(route.getSource())) {
            addCity(route.getSource());
        }
        if (!adjList.containsKey(route.getDestination())) {
            addCity(route.getDestination());
        }

        // Add route in both directions (bidirectional graph)
        adjList.get(route.getSource()).add(route);
        adjList.get(route.getDestination()).add(new Route(route.getDestination(), route.getSource(), route.getCost(), route.getTime(), route.getTransportType()));
    }
    @Override
    public List<Route> getRoutesFromCity(City city) {
        return adjList.get(city);
    }
    @Override
    public List<City> getCities() {
        return new ArrayList<>(adjList.keySet());
    }
}
