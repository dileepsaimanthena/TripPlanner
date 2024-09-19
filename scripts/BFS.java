package scripts;

import java.util.List;

public interface BFS {
    List<City> findDirectRoute(Graph graph, City start, City destination);
}

