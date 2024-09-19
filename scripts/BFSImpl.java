package scripts;

import java.util.*;

public class BFSImpl implements BFS {

    @Override
    public List<City> findDirectRoute(Graph graph, City start, City destination) {
        Queue<City> queue = new LinkedList<>();
        Map<City, City> prev = new HashMap<>();
        Set<City> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            City current = queue.poll();
            if (current.equals(destination)) break;

            for (Route route : graph.getRoutesFromCity(current)) {
                if (!visited.contains(route.getDestination())) {
                    queue.add(route.getDestination());
                    visited.add(route.getDestination());
                    prev.put(route.getDestination(), current);
                }
            }
        }

        List<City> path = new ArrayList<>();
        for (City at = destination; at != null; at = prev.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path.size() > 1 ? path : null;
    }
}

