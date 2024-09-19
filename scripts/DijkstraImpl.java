package scripts;

import java.util.*;

public class DijkstraImpl implements Dijkstra {
    @Override
    public Map<City, Double> findShortestPath(Graph graph, City start, City destination, Comparator<Route> comparator) {
        Map<City, Double> distances = new HashMap<>();
        PriorityQueue<City> queue = new PriorityQueue<>(Comparator.comparingDouble(distances::get));
        Map<City, City> prev = new HashMap<>();
        distances.put(start, 0.0);
        queue.add(start);
        while (!queue.isEmpty()) {
            City current = queue.poll();
            if (current.equals(destination)) break;
            for (Route route : graph.getRoutesFromCity(current)) {
                double newDist = distances.get(current) + (comparator.compare(route, route) == 0 ? route.getTime() : route.getCost());
                if (newDist < distances.getOrDefault(route.getDestination(), Double.POSITIVE_INFINITY)) {
                    distances.put(route.getDestination(), newDist);
                    queue.add(route.getDestination());
                    prev.put(route.getDestination(), current);
                }
            }
        }
        return distances;
    }
}