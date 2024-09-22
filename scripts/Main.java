package scripts;
import java.io.IOException;
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
        graph.printGraph();
        TripPlannerController controller = new TripPlannerController(graph);

        String startCityName;
        String destinationCityName;
        String priority;

        if (args.length >= 3) {
            startCityName = args[0];
            destinationCityName = args[1];
            priority = args[2].toLowerCase();
        } else {
            System.out.println("Please provide start city, destination city, and priority.");
            return;
        }

        City startCity = getCityByName(graph, startCityName);
        City destinationCity = getCityByName(graph, destinationCityName);

        if (startCity == null || destinationCity == null) {
            System.out.println("Invalid city names.");
            return;
        }

        // Get the path and total cost in the desired format
        String result = controller.getRoute(startCity, destinationCity, priority);

        // Output the result
        System.out.println(result);
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




