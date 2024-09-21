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

        // Display cities and routes
        for (City city : graph.getCities()) {
            System.out.println("City: " + city.getName());
            for (Route route : graph.getRoutesFromCity(city)) {
                System.out.println("Route from " + route.getSource().getName() + " to " + route.getDestination().getName() + " (Cost: " + route.getCost() + ", Time: " + route.getTime() + ")");
            }
        }

        TripPlannerController controller = new TripPlannerController(graph);
        
        String startCityName;
        String destinationCityName;
        String priority;

        if (args.length >= 3) {
            // Join all but the last argument for the start city name
            startCityName = String.join(" ", java.util.Arrays.copyOfRange(args, 0, args.length - 2));
            destinationCityName = args[args.length - 2];
            priority = args[args.length - 1].toLowerCase();
        } else {
            // Fall back to scanner input
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter start city: ");
            startCityName = scanner.nextLine();
            System.out.println("Enter destination city: ");
            destinationCityName = scanner.nextLine();
            System.out.println("Enter route priority (fast, cheap, direct): ");
            priority = scanner.nextLine().toLowerCase();
            scanner.close();
        }

        City startCity = getCityByName(graph, startCityName);
        City destinationCity = getCityByName(graph, destinationCityName);

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



