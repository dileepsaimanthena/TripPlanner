package scripts;

public class TravelController {
    private Graph graph = new Graph();

    public void loadGraph(String citiesFile, String routesFile) {
        CSVParser.parseCities(citiesFile, graph);
        CSVParser.parseRoutes(routesFile, graph);
    }

    public String getRoute(String sourceName, String destinationName, String priority) {
        City source = getCityByName(sourceName);
        City destination = getCityByName(destinationName);

        if (source == null || destination == null) {
            return "Invalid source or destination city.";
        }

        switch (priority.toLowerCase()) {
            case "fast":
                return Dijkstra.findFastestRoute(graph, source, destination);
            case "cheap":
                return Dijkstra.findCheapestRoute(graph, source, destination);
            case "direct":
                return BFS.findDirectRoute(graph, source, destination);
            default:
                return "Invalid priority.";
        }
    }

    private City getCityByName(String name) {
        for (City city : graph.getCities()) {
            if (city.getName().equalsIgnoreCase(name)) {
                return city;
            }
        }
        return null;
    }
}
