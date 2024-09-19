package scripts;

import java.util.*;

public class GraphImpl implements Graph {
    private Map<City, List<Route>> adjList = new HashMap<>();

    @Override
    public void addCity(City city) {
        adjList.putIfAbsent(city, new ArrayList<>());
    }

    @Override
    public void addRoute(Route route) {
        adjList.get(route.getSource()).add(route);
    }

    @Override
    public List<Route> getRoutesFromCity(City city) {
        return adjList.get(city);
    }

    @Override
    public List<City> getCities() {
        return new ArrayList<>(adjList.keySet());
    }
}

