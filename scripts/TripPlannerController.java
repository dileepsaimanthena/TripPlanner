package scripts;

import java.util.Comparator;
import java.util.List;

public class TripPlannerController {
    private Graph graph;

    public TripPlannerController(Graph graph) {
        this.graph = graph;
    }

    public String getRoute(City start, City destination, String priority) {
        if (priority.equals("fast")) {
            Dijkstra dijkstra = new DijkstraImpl();
            List<City> fastestPath = dijkstra.findShortestPath(graph, start, destination, Comparator.comparingDouble(Route::getTime));
            double totalTime = calculateTotalTime(fastestPath);
            double totalCost = calculateTotalCost(fastestPath);
            return formatRoute(fastestPath, totalTime, totalCost, "Fastest Route");

        } else if (priority.equals("cheap")) {
            Dijkstra dijkstra = new DijkstraImpl();
            List<City> cheapestPath = dijkstra.findShortestPath(graph, start, destination, Comparator.comparingDouble(Route::getCost));
            double totalTime = calculateTotalTime(cheapestPath);
            double totalCost = calculateTotalCost(cheapestPath);
            return formatRoute(cheapestPath, totalTime, totalCost, "Cheapest Route");

        } else {
            BFS bfs = new BFSImpl();
            List<City> directRoute = bfs.findDirectRoute(graph, start, destination);
            if (directRoute.isEmpty()) {
                return "No direct route found from " + start.getName() + " to " + destination.getName();
            }
            double totalTime = calculateTotalTime(directRoute);
            double totalCost = calculateTotalCost(directRoute);
            return formatRoute(directRoute, totalTime, totalCost, "Direct Route");
        }
    }

    // Helper method to calculate the total cost
    private double calculateTotalCost(List<City> path) {
        double totalCost = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            City current = path.get(i);
            City next = path.get(i + 1);
            for (Route route : graph.getRoutesFromCity(current)) {
                if (route.getDestination().equals(next)) {
                    totalCost += route.getCost();
                    break;
                }
            }
        }
        return totalCost;
    }
    private double calculateTotalTime(List<City> path) {
        double totalTime = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            City current = path.get(i);
            City next = path.get(i + 1);
            for (Route route : graph.getRoutesFromCity(current)) {
                if (route.getDestination().equals(next)) {
                    totalTime += route.getTime();
                    break;
                }
            }
        }
        return totalTime;
    }

    private String formatRoute(List<City> path, double totalTime, double totalCost, String routeType) {
        StringBuilder routeString = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            routeString.append(path.get(i).getName());
            if (i < path.size() - 1) {
                routeString.append(" -> ");
            }
        }
        routeString.append(" | Total Cost: ₹").append(totalCost).append(", Total Time: ").append(totalTime).append(" minutes");

        return routeType + ": " + routeString.toString();
    }
}
