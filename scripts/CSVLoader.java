package scripts;
import java.io.*;
import java.util.*;

public class CSVLoader {

    private Map<String, City> cityMap = new HashMap<>();  // Ensure unique cities

    public void loadCities(Graph graph, String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        reader.readLine();  // Skip header
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            City city = getOrCreateCity(fields[0]);
            graph.addCity(city);
            System.out.println("City loaded: " + city.getName());
        }
        reader.close();
    }

    public void loadRoutes(Graph graph, String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        reader.readLine();  // Skip header
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            City source = getOrCreateCity(fields[0]);
            City destination = getOrCreateCity(fields[1]);
            double cost = Double.parseDouble(fields[2]);
            double time = Double.parseDouble(fields[3]);
            String transportType = fields[4];
            
            Route route = new Route(source, destination, cost, time, transportType);
            graph.addRoute(route);
            System.out.println("Route loaded: " + source.getName() + " -> " + destination.getName() + 
                               " (Cost: " + cost + ", Time: " + time + ")");
        }
        reader.close();
    }

    // Helper method to ensure cities are unique
    private City getOrCreateCity(String cityName) {
        return cityMap.computeIfAbsent(cityName, City::new);
    }
}



