package scripts;
import java.util.Comparator;
import java.util.Map;

public interface Dijkstra {
    Map<City, Double> findShortestPath(Graph graph, City start, City destination, Comparator<Route> comparator);
}