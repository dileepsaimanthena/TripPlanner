package scripts;

import java.util.*;

public class BFS {

    public static String findDirectRoute(Graph graph, City start, City destination) {
        Queue<City> queue = new LinkedList<>();
        Map<City, Route> prevRoute = new HashMap<>();
        Set<City> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            City current = queue.poll();
            if (current.equals(destination)) {
                return reconstructPath(prevRoute, start, destination);
            }

            for (Route route : graph.getRoutesFromCity(current)) {
                City nextCity = route.getDestination();
                if (!visited.contains(nextCity)) {
                    queue.add(nextCity);
                    visited.add(nextCity);
                    prevRoute.put(nextCity, route);
                }
            }
        }
        return "No direct route found.";
    }

    private static String reconstructPath(Map<City, Route> prevRoute, City start, City destination) {
        List<Route> path = new ArrayList<>();
        City currentCity = destination;

        // Reconstruct the path from destination to start
        while (prevRoute.containsKey(currentCity)) {
            Route route = prevRoute.get(currentCity);
            path.add(route);
            currentCity = route.getSource();
        }
        Collections.reverse(path);  // Reverse the path to show it from start to destination

        StringBuilder routeString = new StringBuilder();
        double totalCost = 0;
        double totalTime = 0;

        for (Route route : path) {
            routeString.append(route.getSource().getName())
                       .append(" -> ")
                       .append(route.getDestination().getName())
                       .append(" [")
                       .append(route.getTransportType())
                       .append(", Cost: ").append(route.getCost())
                       .append(", Time: ").append(route.getTime())
                       .append("] ");
            totalCost += route.getCost();
            totalTime += route.getTime();
        }

        return routeString.toString() + "| Total Cost: " + totalCost + ", Total Time: " + totalTime;
    }
}
