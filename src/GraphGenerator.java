import api.*;

//this class generate a random graph
public class GraphGenerator {
    private static int id=0;

    /**function that creates a new random graph in a chosen size
     *
     * @param size
     * @return
     */
    public static DirectedWeightedGraph GraphGenerator(int size){
        DirectedWeightedGraph graph = new DirectedWeightedGraphImpl();
        for (int i = 0; i < size; i++) {
            graph.addNode(generateNodeData());
        }
        if (size>100){
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < 20; j++) {
                    int dest=(int)(Math.random()*size);
                    double weight = graph.getNode(i).getLocation().distance(graph.getNode(dest).getLocation())+Math.random()*2;
                    graph.connect(i,dest,weight);
                }
            }
        }
        return graph;
    }

    /**this function generates and save a graph to json
     *
     * @param size
     */
    public static void generateAndSave(int size){
        DirectedWeightedGraph graph = new DirectedWeightedGraphImpl();
        graph = GraphGenerator(size);
        DirectedWeightedGraphAlgorithms graphAlgorithms = new DirectedWeightedGraphAlgorithmsImpl();
        graphAlgorithms.init(graph);
        graphAlgorithms.save(size+"Nodes.json");
    }

    private static GeoLocationImpl generateGeoLocation(){
        double x = Math.random()*1000;
        double y = Math.random()*1000;
        GeoLocationImpl geoLocation = new GeoLocationImpl(x,y,0);
        return geoLocation;
    }

    private static NodeDataImpl generateNodeData(){
        NodeDataImpl nodeData= new NodeDataImpl(generateGeoLocation(),id);
        id=id+1;
        return nodeData;
    }
}
