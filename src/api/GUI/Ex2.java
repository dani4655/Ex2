package api.Gui;

import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.DirectedWeightedGraphAlgorithmsImpl;
import api.DirectedWeightedGraphImpl;
import api.Gui.MenuBar;

/**
 * This class is the main class for api.Gui.Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {
    /**
     * This static function will be used to test your implementation
     *
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGrapg(String json_file) {
        DirectedWeightedGraphAlgorithms graph = new DirectedWeightedGraphAlgorithmsImpl();
        if (graph.load(json_file))
            return graph.getGraph();
        return new DirectedWeightedGraphImpl();
    }

    /**
     * This static function will be used to test your implementation
     *
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraphAlgorithms graph = new DirectedWeightedGraphAlgorithmsImpl();
        if (graph.load(json_file))
            return graph;
        return new DirectedWeightedGraphAlgorithmsImpl();
    }

    /**
     * This static function will run your GUI using the json fime.
     *
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     */
    public static void runGUI(String json_file) {
        DirectedWeightedGraphAlgorithms graph = new DirectedWeightedGraphAlgorithmsImpl();
        graph.load(json_file);
        MenuBar m = new MenuBar(json_file);
    }

    public static void main(String[] args) {
            runGUI(args[0]);
    }
}