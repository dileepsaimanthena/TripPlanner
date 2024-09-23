package scripts;

import java.util.*;

public class Graph {
    private Map<City, List<Route>> adjList = new HashMap<>();

    public void addCity(City city) {
        adjList.putIfAbsent(city, new ArrayList<>());
    }

    public void addRoute(City source, City destination, double cost, double time, String transportType) {
        Route route = new Route(source, destination, cost, time, transportType);
        adjList.get(source).add(route);
    }    
    public List<Route> getRoutesFromCity(City city) {
        return adjList.getOrDefault(city, new ArrayList<>());
    }
    public Set<City> getCities() {
        return adjList.keySet();
    }
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
