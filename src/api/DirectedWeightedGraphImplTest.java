package api;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class DirectedWeightedGraphImplTest {
    GeoLocationImpl p1= new GeoLocationImpl(2.2,1,5);
    NodeDataImpl nodeData = new NodeDataImpl(p1, 1, 5, 5.5, "meta");
    HashMap<Integer, NodeData> node = new HashMap<Integer, NodeData>();
    HashMap<Integer, HashMap<Integer, EdgeData>> edge = new HashMap<Integer, HashMap<Integer, EdgeData>>();
    DirectedWeightedGraphImpl de = new DirectedWeightedGraphImpl(node, edge);



    @Test
    void getNode() {


    }

    @Test
    void getEdge() {
    }

    @Test
    void addNode() {
    }

    @Test
    void connect() {
    }

    @Test
    void nodeIter() {
    }

    @Test
    void edgeIter() {
    }

    @Test
    void testEdgeIter() {
    }

    @Test
    void removeNode() {
    }

    @Test
    void removeEdge() {
    }

    @Test
    void nodeSize() {
    }

    @Test
    void edgeSize() {
    }

    @Test
    void getMC() {
    }
}