package scripts;
import java.util.Comparator;
import java.util.List;

public interface Dijkstra {
    List<City> findShortestPath(Graph graph, City start, City destination, Comparator<Route> comparator);
}