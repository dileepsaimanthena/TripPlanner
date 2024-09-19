package scripts;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Graph graph = new GraphImpl();
        CSVLoader loader = new CSVLoader();
        try {
            loader.loadCities(graph, "data/cities.csv");
            loader.loadRoutes(graph, "data/routes.csv");
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
            return;
        }
        TripPlannerController controller = new TripPlannerController(graph);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter start city: ");
        String startCityName = scanner.nextLine();
        City startCity = getCityByName(graph, startCityName);
        scanner.close();
        System.out.println("Enter destination city: ");
        String destinationCityName = scanner.nextLine();
        City destinationCity = getCityByName(graph, destinationCityName);
        System.out.println("Enter route priority (fast, cheap, direct): ");
        String priority = scanner.nextLine().toLowerCase();
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

