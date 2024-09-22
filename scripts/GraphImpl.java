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
        if (!adjList.containsKey(route.getSource())) {
            addCity(route.getSource());
        }
        if (!adjList.containsKey(route.getDestination())) {
            addCity(route.getDestination());
        }
        adjList.get(route.getSource()).add(route);
        // Add the reverse route for undirected graph
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

    // Method to print the graph
    public void printGraph() {
        for (Map.Entry<City, List<Route>> entry : adjList.entrySet()) {
            City city = entry.getKey();
            System.out.print("City: " + city.getName() + " -> ");
            List<Route> routes = entry.getValue();
            for (Route route : routes) {
                System.out.print(route.getDestination().getName() + " (Cost: " + route.getCost() + ", Time: " + route.getTime() + ") ");
            }
            System.out.println();
        }
    }
}

