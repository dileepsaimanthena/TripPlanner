package scripts;
import java.util.List;

public interface Graph {
    void addCity(City city);
    void addRoute(Route route);
    List<Route> getRoutesFromCity(City city);
    List<City> getCities();  // Needed to fetch all cities for searching
}
