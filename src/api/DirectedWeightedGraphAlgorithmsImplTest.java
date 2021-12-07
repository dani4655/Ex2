package api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;

class DirectedWeightedGraphAlgorithmsImplTest {
    GeoLocationImpl g1 = new GeoLocationImpl(0,0,0);
    GeoLocationImpl g2 = new GeoLocationImpl(1,1,1.5);
    GeoLocationImpl g3 = new GeoLocationImpl(5,2,1.5);
    NodeDataImpl n1 = new NodeDataImpl(g1,0,0,1.5,"moshe");
    NodeDataImpl n2 = new NodeDataImpl(g2, 1, 1, 3,"boaz");
    NodeDataImpl n3 = new NodeDataImpl(g3, 2, 2, 5,"ben");
    EdgeDataImpl e1 = new EdgeDataImpl(1,2, 3, 1,"boaz ben");
    EdgeDataImpl e2 = new EdgeDataImpl(2,0 ,0.5, 2,"boaz ben moshe");
    DirectedWeightedGraph dwg= new DirectedWeightedGraphImpl();
    HashMap<Integer, NodeDataImpl> h1 = new HashMap();
    DirectedWeightedGraphAlgorithmsImpl boazBenMoshe = new DirectedWeightedGraphAlgorithmsImpl(dwg);
    DirectedWeightedGraphImpl dg = new DirectedWeightedGraphImpl();

    NodeDataImpl np1 = new NodeDataImpl(new GeoLocationImpl(0,0,0),0,0,1.5,"");
    NodeDataImpl np2 = new NodeDataImpl(new GeoLocationImpl(1,1,0), 1, 1, 3,"");
    NodeDataImpl np3 = new NodeDataImpl(new GeoLocationImpl(3,5,0), 2, 2, 5,"");
    DirectedWeightedGraph dg2= new DirectedWeightedGraphImpl();

    @BeforeEach
    void setup(){
        dg.addNode(n1);
        dg.addNode(n2);
        dg.addNode(n3);
        int tag;
        String s;
        tag = e1.getTag();
        s=e1.getInfo();
        dg.connect(e1.getSrc(),e1.getDest(),e1.getWeight());
        dg.getEdge(e1.getSrc(),e1.getDest()).setTag(tag);
        dg.getEdge(e1.getSrc(),e1.getDest()).setInfo(s);
        tag = e2.getTag();
        s=e2.getInfo();
        dg.connect(e2.getSrc(),e2.getDest(),e2.getWeight());
        dg.getEdge(e2.getSrc(),e2.getDest()).setTag(tag);
        dg.getEdge(e2.getSrc(),e2.getDest()).setInfo(s);
        DirectedWeightedGraphAlgorithmsImpl boaz = new DirectedWeightedGraphAlgorithmsImpl(dg);
        dg2.addNode(np1);
        dg2.addNode(np2);
        dg2.addNode(np3);

        dg2.connect(0,1,np1.getLocation().distance(np2.getLocation()));
        dg2.connect(0,2,np1.getLocation().distance(np3.getLocation()));
        dg2.connect(1,0,np2.getLocation().distance(np1.getLocation()));
        dg2.connect(1,2,np2.getLocation().distance(np3.getLocation()));
        dg2.connect(2,0,np3.getLocation().distance(np1.getLocation()));
        dg2.connect(2,1,np3.getLocation().distance(np2.getLocation()));
        dg2.connect(0,0,np1.getLocation().distance(np1.getLocation()));
        dg2.connect(1,1,np2.getLocation().distance(np2.getLocation()));
        dg2.connect(2,2,np3.getLocation().distance(np3.getLocation()));

    }


    @Test
    void init() {

    }

    @Test
    void getGraph() {
    }

    @Test
    void copy() {
        DirectedWeightedGraphAlgorithmsImpl dtest = new DirectedWeightedGraphAlgorithmsImpl(dg);
        dtest.init(dg);
        DirectedWeightedGraph d = dtest.copy();
        assertNotEquals(d,dg);
//        System.out.println(d.edgeSize()+" "+dg.edgeSize());
//        System.out.println(d.getNode(0).getKey()+" "+dg.getNode(0).getKey());
        assertEquals(d.edgeSize(),dg.edgeSize());
        assertNotEquals(d.getNode(0),dg.getNode(0));
        assertEquals(d.getNode(0).getTag(), dg.getNode(0).getTag());
        assertEquals(d.getNode(0).getWeight(), dg.getNode(0).getWeight());
        assertEquals(d.getNode(0).getInfo(), dg.getNode(0).getInfo());
        assertEquals(d.getNode(0).getLocation(), dg.getNode(0).getLocation());
        assertEquals(d.getNode(0).getKey(), dg.getNode(0).getKey());
        assertNotEquals(d.getNode(1),dg.getNode(1));
        assertEquals(d.getNode(1).getTag(), dg.getNode(1).getTag());
        assertEquals(d.getNode(1).getWeight(), dg.getNode(1).getWeight());
        assertEquals(d.getNode(1).getInfo(), dg.getNode(1).getInfo());
        assertEquals(d.getNode(1).getLocation(), dg.getNode(1).getLocation());
        assertEquals(d.getNode(1).getKey(), dg.getNode(1).getKey());
        d.getNode(0).setTag(10);
        assertNotEquals(d.getNode(0).getTag(), dg.getNode(0).getTag());
        d.getNode(0).setWeight(100);
        assertNotEquals(d.getNode(0).getWeight(), dg.getNode(0).getWeight());
        d.getNode(0).setInfo("loBoaz");
        assertNotEquals(d.getNode(0).getInfo(), dg.getNode(0).getInfo());
        d.getNode(0).setLocation(new GeoLocationImpl(100,1,5));
        assertNotEquals(d.getNode(0).getLocation(), dg.getNode(0).getLocation());

    }

    @Test
    void isConnected() {
        DirectedWeightedGraphAlgorithmsImpl dtest = new DirectedWeightedGraphAlgorithmsImpl(dg);
        assertFalse(dtest.isConnected());
        DirectedWeightedGraphAlgorithmsImpl fargah = new DirectedWeightedGraphAlgorithmsImpl(dg2);
        assertTrue(fargah.isConnected());

    }

    @Test
    void shortestPathDist() {
        DirectedWeightedGraphAlgorithmsImpl dtest = new DirectedWeightedGraphAlgorithmsImpl(dg);
        double le= dtest.shortestPathDist(0,0);
        assertEquals(le,0);

    }

    @Test
    void shortestPath() {
    }

    @Test
    void center() {
        DirectedWeightedGraphAlgorithmsImpl g1 = new DirectedWeightedGraphAlgorithmsImpl(dg2);

    }

    @Test
    void tsp() {
    }

    @Test
    void save() {
    }

    @Test
    void load() {
        boazBenMoshe.load("data/G1.json");
    }

    public static void main(String[] args) {

    }
}