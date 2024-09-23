package scripts;
public class Main {
    public static void main(String[] args) {
        if (args.length < 3) {
            return;
        }
        String source = args[0];
        String destination = args[1];
        String priority = args[2];
    
        TravelController controller = new TravelController();
        controller.loadGraph("public/cities.csv", "public/routes.csv");
        
        String route = controller.getRoute(source, destination, priority);
        System.out.println(route);
    }    
}
