package api;

import org.junit.jupiter.api.Test;


import java.util.HashMap;



import static org.junit.jupiter.api.Assertions.*;

class DirectedWeightedGraphImplTest {
    GeoLocationImpl p1= new GeoLocationImpl(2.2,1,5);
    GeoLocationImpl p2= new GeoLocationImpl(3.5,22,20);
    EdgeDataImpl a = new EdgeDataImpl(4, 2, 1.0,4,"my name is edge" );
    NodeDataImpl nodeData = new NodeDataImpl(p1, 2, 5, 5.5, "meta");
    NodeDataImpl nodea = new NodeDataImpl(p2, 4, 4, 1, "my name is edge");
    HashMap<Integer, NodeData> node = new HashMap<Integer, NodeData>();
    HashMap<Integer, HashMap<Integer, EdgeData>> edge = new HashMap<Integer, HashMap<Integer, EdgeData>>();
    DirectedWeightedGraph de = new DirectedWeightedGraphImpl(node, edge);




    @Test
    void getNode() {
        de.addNode(nodeData);
        assertEquals(nodeData,de.getNode(2));
        assertNotEquals(nodeData,de.getNode(4));
    }

    @Test
    void getEdge() {
        de.addNode(nodea);
        de.addNode(nodeData);
        de.connect(4,2,1.0);
        de.getEdge(4,2).setTag(4);
        de.getEdge(4,2).setInfo("my name is edge");
        assertEquals(a.getInfo(),de.getEdge(4,2).getInfo());
        assertEquals(a.getWeight(),de.getEdge(4,2).getWeight());
        assertEquals(a.getTag(),de.getEdge(4,2).getTag());
    }

    @Test
    void addNode() {
        de.addNode(nodeData);
        assertEquals(nodeData,de.getNode(2));
        assertNotEquals(nodeData,de.getNode(4));
    }

    @Test
    void connect() {
        de.addNode(nodea);
        de.addNode(nodeData);
        de.connect(4,2,1.0);
        de.getEdge(4,2).setTag(4);
        de.getEdge(4,2).setInfo("my name is edge");
        assertEquals(a.getInfo(),de.getEdge(4,2).getInfo());
        assertEquals(a.getWeight(),de.getEdge(4,2).getWeight());
        assertEquals(a.getTag(),de.getEdge(4,2).getTag());
    }

    @Test
    void nodeIter() {
        de.addNode(nodea);
        de.addNode(nodeData);
        de.connect(4,2,1.0);
        assertEquals(2, de.nodeIter().next().getKey());
        de.removeNode(2);
        assertEquals(4, de.nodeIter().next().getKey());
    }

    @Test
    void edgeIter() {
        de.addNode(nodea);
        de.addNode(nodeData);
        de.connect(4,2,1.0);
        assertEquals(4, de.edgeIter().next().getSrc());

    }

    @Test
    void testEdgeIter() {
        de.addNode(nodea);
        de.addNode(nodeData);
        de.connect(4,2,1.0);
        assertEquals(4, de.edgeIter(4).next().getSrc());
    }

    @Test
    void removeNode() {
        de.addNode(nodeData);
        assertEquals(nodeData,de.getNode(2));
        de.removeNode(2);
        assertEquals(null,de.getNode(2));
    }

    @Test
    void removeEdge() {
        de.addNode(nodea);
        de.addNode(nodeData);
        de.connect(4,2,1.0);
        de.removeEdge(4,2);
        assertEquals(null,de.getEdge(4,2));
    }

    @Test
    void nodeSize() {
        de.addNode(nodeData);
        de.addNode(nodea);
        assertEquals(2, de.nodeSize());
    }

    @Test
    void edgeSize() {
        de.addNode(nodea);
        de.addNode(nodeData);
        de.connect(4,2,1.0);
        assertEquals(1, de.edgeSize());
    }

    @Test
    void getMC() { //must test all together
        de.addNode(nodea);
        de.addNode(nodeData);
        de.connect(4,2,1.0);
        assertEquals(11,de.getMC()); // 3
        de.removeEdge(4,2);
        assertEquals(10,de.getMC()); // 2
    }
}