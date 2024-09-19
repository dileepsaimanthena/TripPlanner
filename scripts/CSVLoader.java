package scripts;
import java.io.*;
public class CSVLoader {

    public void loadCities(Graph graph, String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        reader.readLine();  // Skip header
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            graph.addCity(new City(fields[0]));
        }
        reader.close();
    }

    public void loadRoutes(Graph graph, String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        reader.readLine();  // Skip header
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            City source = new City(fields[0]);
            City destination = new City(fields[1]);
            double cost = Double.parseDouble(fields[2]);
            double time = Double.parseDouble(fields[3]);
            String transportType = fields[4];
            graph.addRoute(new Route(source, destination, cost, time, transportType));
        }
        reader.close();
    }
}

