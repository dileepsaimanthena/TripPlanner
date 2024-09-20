package scripts;

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
            return "Fastest Route: " + fastestPath.toString();
        } else if (priority.equals("cheap")) {
            Dijkstra dijkstra = new DijkstraImpl();
            Map<City, Double> cheapestPath = dijkstra.findShortestPath(graph, start, destination, Comparator.comparingDouble(Route::getCost));
            return "Cheapest Route: " + cheapestPath.toString();
        } else {
            BFS bfs = new BFSImpl();
            List<City> directRoute = bfs.findDirectRoute(graph, start, destination);
            return "Direct Route: " + directRoute.toString();
        }
    }    
}

