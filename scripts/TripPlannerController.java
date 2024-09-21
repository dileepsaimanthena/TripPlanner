package scripts;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class TripPlannerController {
    private Graph graph;

    public TripPlannerController(Graph graph) {
        this.graph = graph;
    }

    public String getRoute(City start, City destination, String priority) {
        if (priority.equals("fast")) {
            Dijkstra dijkstra = new DijkstraImpl();
            Map<City, Double> fastestPath = dijkstra.findShortestPath(graph, start, destination, Comparator.comparingDouble(Route::getTime));
            return formatRoute(fastestPath, "Fastest Route");
        } else if (priority.equals("cheap")) {
            Dijkstra dijkstra = new DijkstraImpl();
            Map<City, Double> cheapestPath = dijkstra.findShortestPath(graph, start, destination, Comparator.comparingDouble(Route::getCost));
            return formatRoute(cheapestPath, "Cheapest Route");
        } else {
            BFS bfs = new BFSImpl();
            List<City> directRoute = bfs.findDirectRoute(graph, start, destination);
            return formatDirectRoute(directRoute, "Direct Route");
        }
    }
    private String formatRoute(Map<City, Double> path, String routeType) {
    StringBuilder routeString = new StringBuilder();
    double totalCostOrTime = 0.0;
    List<City> citiesInPath = new ArrayList<>(path.keySet());
    totalCostOrTime = path.get(citiesInPath.get(citiesInPath.size() - 1));
    for (int i = 0; i < citiesInPath.size(); i++) {
        routeString.append(citiesInPath.get(i).getName());
        if (i < citiesInPath.size() - 1) {
            routeString.append(" -> ");
        }
    }
    routeString.append(" total cost/time: ").append(totalCostOrTime);

    return routeType + ": " + routeString.toString();
    }
    private String formatDirectRoute(List<City> path, String routeType) {
        StringBuilder routeString = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            routeString.append(path.get(i).getName());
            if (i < path.size() - 1) {
                routeString.append(" -> ");
            }
        }
    
        return routeType + ": " + routeString.toString();
    }
}

