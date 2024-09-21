package scripts;
import java.util.*;
public class DijkstraImpl implements Dijkstra {
    @Override
    public Map<City, Double> findShortestPath(Graph graph, City start, City destination, Comparator<Route> comparator) {
        Map<City, Double> distances = new HashMap<>();
        PriorityQueue<City> queue = new PriorityQueue<>(Comparator.comparingDouble(distances::get));
        Map<City, City> prev = new HashMap<>();
        Set<City> visited = new HashSet<>();  // Track visited cities
    
        // Initialize distances and queue
        distances.put(start, 0.0);
        queue.add(start);
        while (!queue.isEmpty()) {
            City current = queue.poll();
    
            // Skip if the city has already been visited
            if (visited.contains(current)) {
                continue;
            }
    
            // Mark the city as visited
            visited.add(current);
    
            // If we reached the destination, stop
            if (current.equals(destination)) {
                break;
            }
    
            // Explore all routes from the current city
            for (Route route : graph.getRoutesFromCity(current)) {
                City nextCity = route.getDestination();
    
                // Skip reverse routes that go back to the current city
                if (visited.contains(nextCity)) {
                    continue;  // Don't revisit cities
                }
    
                // Calculate the new distance based on the comparator (either time or cost)
                double newDist = distances.get(current) + (comparator.compare(route, route) == 0 ? route.getTime() : route.getCost());
    
                // If a shorter path is found to nextCity, update the distances and previous cities
                if (newDist < distances.getOrDefault(nextCity, Double.POSITIVE_INFINITY)) {
                    distances.put(nextCity, newDist);
                    prev.put(nextCity, current);
                    queue.add(nextCity);
                }
            }
        }
    
        // If no path was found to the destination, return an empty map
        if (!distances.containsKey(destination)) {
            System.out.println("No path found to " + destination.getName());
            return Collections.emptyMap();
        }
    
        // Reconstruct and return the path and distances
        return reconstructPath(prev, start, destination, distances);
    }
    // Helper method to reconstruct the path
    private Map<City, Double> reconstructPath(Map<City, City> prev, City start, City destination, Map<City, Double> distances) {
        List<City> path = new ArrayList<>();
        City at = destination;
    
        // Reconstruct the path by tracing back from the destination to the start
        while (at != null && !at.equals(start)) {
            path.add(at);
            at = prev.get(at);  // Move to the previous city in the path
            if (at == null) {
                System.out.println("Error: Previous city is null for " + destination.getName());
            }
        }
    
        // Add the start city and reverse the path
        path.add(start);
        Collections.reverse(path);
        // Return the distance and path information
        return distances;
    }    
}