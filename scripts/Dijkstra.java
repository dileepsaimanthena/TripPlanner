package scripts;

import java.util.*;

public class Dijkstra {

    public static String findFastestRoute(Graph graph, City start, City destination) {
        return findRoute(graph, start, destination, Comparator.comparingDouble(Route::getTime), "fastest");
    }

    public static String findCheapestRoute(Graph graph, City start, City destination) {
        return findRoute(graph, start, destination, Comparator.comparingDouble(Route::getCost), "cheapest");
    }

    private static String findRoute(Graph graph, City start, City destination, Comparator<Route> comparator, String routeType) {
        Map<City, Double> distances = new HashMap<>();
        Map<City, Route> prevRoute = new HashMap<>();
        PriorityQueue<City> pq = new PriorityQueue<>(Comparator.comparingDouble(distances::get));

        distances.put(start, 0.0);
        pq.add(start);

        while (!pq.isEmpty()) {
            City current = pq.poll();
            if (current.equals(destination)) {
                return reconstructPath(prevRoute, start, destination, distances.get(destination), routeType);
            }

            for (Route route : graph.getRoutesFromCity(current)) {
                City nextCity = route.getDestination();
                double newDist = distances.get(current) + (routeType.equals("fastest") ? route.getTime() : route.getCost());

                if (newDist < distances.getOrDefault(nextCity, Double.POSITIVE_INFINITY)) {
                    distances.put(nextCity, newDist);
                    prevRoute.put(nextCity, route);
                    pq.add(nextCity);
                }
            }
        }
        return "No route found.";
    }

    private static String reconstructPath(Map<City, Route> prevRoute, City start, City destination, double totalCostOrTime, String routeType) {
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
