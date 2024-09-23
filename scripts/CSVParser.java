package scripts;
import java.io.*;
public class CSVParser {

    // Method to parse cities from a CSV file and add them to the graph
    public static void parseCities(String filePath, Graph graph) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Each line represents a city name
                City city = new City(line.trim());
                graph.addCity(city);
            }
        } catch (IOException e) {
            System.err.println("Error reading cities CSV file: " + e.getMessage());
        }
    }

    public static void parseRoutes(String filePath, Graph graph) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;  // Skip the header line
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;  // Skip the header
                }
    
                String[] parts = line.split(",");
                if (parts.length != 5) {
                    System.out.println("Malformed line: " + line);
                    continue;  // Skip malformed lines
                }
    
                // Extract fields: source, destination, cost, time, and transportType
                String sourceName = parts[0].trim();
                String destinationName = parts[1].trim();
                double cost = Double.parseDouble(parts[2].trim());
                double time = Double.parseDouble(parts[3].trim());
                String transportType = parts[4].trim();  // Include transport type
    
                // Create City objects for source and destination
                City source = new City(sourceName);
                City destination = new City(destinationName);
    
                // Add cities to the graph if they aren't already there
                graph.addCity(source);
                graph.addCity(destination);
    
                // Add route with transport type to the graph
                graph.addRoute(source, destination, cost, time, transportType);
            }
        } catch (IOException e) {
            System.err.println("Error reading routes CSV file: " + e.getMessage());
        }
    }      
}
 