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
            double totalTime = calculateTotalCostOrTime(fastestPath, "fast");  // Calculate total time
            return formatRoute(fastestPath, totalTime, "Fastest Route");

        } else if (priority.equals("cheap")) {
            Dijkstra dijkstra = new DijkstraImpl();
            List<City> cheapestPath = dijkstra.findShortestPath(graph, start, destination, Comparator.comparingDouble(Route::getCost));
            double totalCost = calculateTotalCostOrTime(cheapestPath, "cheap");  // Calculate total cost
            return formatRoute(cheapestPath, totalCost, "Cheapest Route");

        } else {
            BFS bfs = new BFSImpl();
            List<City> directRoute = bfs.findDirectRoute(graph, start, destination);
            if (directRoute.isEmpty()) {
                return "No direct route found from " + start.getName() + " to " + destination.getName();
            }
            return formatRoute(directRoute, 0, "Direct Route");  // Direct route doesn't need cost
        }
    }

    // Helper method to calculate the total cost or time
    private double calculateTotalCostOrTime(List<City> path, String priority) {
        double total = 0.0;

        // Loop through each pair of cities in the path
        for (int i = 0; i < path.size() - 1; i++) {
            City current = path.get(i);
            City next = path.get(i + 1);

            // Find the route between current and next city
            for (Route route : graph.getRoutesFromCity(current)) {
                if (route.getDestination().equals(next)) {
                    // If the priority is 'fast', calculate time; if 'cheap', calculate cost
                    if (priority.equals("fast")) {
                        total += route.getCost();  // Add time for the fastest route
                    } else if (priority.equals("cheap")) {
                        total += route.getCost();  // Add cost for the cheapest route
                    }
                    break;
                }
            }
        }

        return total;  // Return the total cost or time for the entire path
    }

    // Helper method to format the route string
    private String formatRoute(List<City> path, double totalCostOrTime, String routeType) {
        StringBuilder routeString = new StringBuilder();

        // Build the route string like "A -> B -> C"
        for (int i = 0; i < path.size(); i++) {
            routeString.append(path.get(i).getName());
            if (i < path.size() - 1) {
                routeString.append(" -> ");
            }
        }

        // Add the total cost or time
        routeString.append(" cost: ").append(totalCostOrTime);

        return routeType + ": " + routeString.toString();
    }
}
