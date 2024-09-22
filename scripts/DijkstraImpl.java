package scripts;

import java.util.*;

public class DijkstraImpl implements Dijkstra {

    @Override
    public List<City> findShortestPath(Graph graph, City start, City destination, Comparator<Route> comparator) {
        Map<City, Double> distances = new HashMap<>();
        PriorityQueue<City> queue = new PriorityQueue<>(Comparator.comparingDouble(distances::get));
        Map<City, City> prev = new HashMap<>();
        Set<City> visited = new HashSet<>();

        distances.put(start, 0.0);
        queue.add(start);

        while (!queue.isEmpty()) {
            City current = queue.poll();

            if (visited.contains(current)) {
                continue;
            }

            visited.add(current);

            if (current.equals(destination)) {
                break;
            }

            for (Route route : graph.getRoutesFromCity(current)) {
                City nextCity = route.getDestination();

                if (visited.contains(nextCity)) {
                    continue;
                }

                // Calculate the new distance based on the comparator (either time or cost)
                double newDist = distances.get(current) + (comparator.compare(route, route) == 0 ? route.getTime() : route.getCost());

                if (newDist < distances.getOrDefault(nextCity, Double.POSITIVE_INFINITY)) {
                    distances.put(nextCity, newDist);
                    prev.put(nextCity, current);
                    queue.add(nextCity);
                }
            }
        }

        if (!distances.containsKey(destination)) {
            System.out.println("No path found to " + destination.getName());
            return Collections.emptyList();
        }

        // Reconstruct and return the path
        return reconstructPath(prev, start, destination);
    }

    // Helper method to reconstruct the path
    private List<City> reconstructPath(Map<City, City> prev, City start, City destination) {
        List<City> path = new ArrayList<>();
        City at = destination;

        while (at != null && !at.equals(start)) {
            path.add(at);
            at = prev.get(at);
        }

        // Add the start city and reverse the path to get the correct order
        path.add(start);
        Collections.reverse(path);
        return path;
    }
}
