package scripts;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Graph graph = new GraphImpl();
        CSVLoader loader = new CSVLoader();
        try {
            loader.loadCities(graph, "public/cities.csv");
            loader.loadRoutes(graph, "public/routes.csv");

        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
            return;
        }
        for (City city : graph.getCities()) {
            System.out.println("City: " + city.getName());
            for (Route route : graph.getRoutesFromCity(city)) {
                System.out.println("Route from " + route.getSource().getName() + " to " + route.getDestination().getName() + " (Cost: " + route.getCost() + ", Time: " + route.getTime() + ")");
            }
        }        
        TripPlannerController controller = new TripPlannerController(graph);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter start city: ");
        String startCityName = scanner.nextLine();
        City startCity = getCityByName(graph, startCityName);
        System.out.println("Enter destination city: ");
        String destinationCityName = scanner.nextLine();
        City destinationCity = getCityByName(graph, destinationCityName);
        System.out.println("Enter route priority (fast, cheap, direct): ");
        String priority = scanner.nextLine().toLowerCase();
        scanner.close();
        if (startCity == null || destinationCity == null) {
            System.out.println("Invalid city names.");
            return;
        }
        String result = controller.getRoute(startCity, destinationCity, priority);
        System.out.println("Route: " + result);
    }
    private static City getCityByName(Graph graph, String name) {
        for (City city : graph.getCities()) {
            if (city.getName().equalsIgnoreCase(name)) {
                return city;
            }
        }
        return null;
    }
}

