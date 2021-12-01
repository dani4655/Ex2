package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeDataImplTest {
    GeoLocationImpl p1= new GeoLocationImpl(2.2,1,5);
    GeoLocationImpl p2= new GeoLocationImpl(3.5,22,20);
    NodeDataImpl nodeData = new NodeDataImpl(p1, 1, 5, 5.5, "meta");

    @Test
    void copy(){
        assertNotEquals(nodeData, nodeData.copy());
        NodeDataImpl a= nodeData.copy();
        assertEquals(a.getInfo(),nodeData.getInfo());
        assertEquals(a.getLocation(),nodeData.getLocation());
        assertEquals(a.getWeight(),nodeData.getWeight());
        assertEquals(a.getTag(),nodeData.getTag());
    }

    @org.junit.jupiter.api.Test
    void getKey() {
        assertEquals(1,nodeData.getKey());
        assertNotEquals(0,nodeData.getKey());
    }

    @org.junit.jupiter.api.Test
    void getLocation() {
        assertEquals(p1,nodeData.getLocation());
        assertNotEquals(p2,nodeData.getLocation());
    }

    @org.junit.jupiter.api.Test
    void setLocation() {
        nodeData.setLocation(p2);
        assertEquals(p2,nodeData.getLocation());
        assertNotEquals(p1,nodeData.getLocation());
    }

    @org.junit.jupiter.api.Test
    void getWeight() {
        assertEquals(5.5,nodeData.getWeight());
        assertNotEquals(5.1,nodeData.getWeight());
    }

    @org.junit.jupiter.api.Test
    void setWeight() {
        nodeData.setWeight(8.3);
        assertEquals(8.3,nodeData.getWeight());
        assertNotEquals(5.5,nodeData.getWeight());
    }

    @org.junit.jupiter.api.Test
    void getInfo() {
        assertEquals("meta",nodeData.getInfo());
        assertNotEquals("eta",nodeData.getInfo());
    }

    @org.junit.jupiter.api.Test
    void setInfo() {
        nodeData.setInfo("eta");
        assertEquals("eta",nodeData.getInfo());
        assertNotEquals("meta",nodeData.getInfo());
    }

    @org.junit.jupiter.api.Test
    void getTag() {
        assertEquals(5, nodeData.getTag());
        assertNotEquals(1, nodeData.getTag());
    }

    @org.junit.jupiter.api.Test
    void setTag() {
        nodeData.setTag(1);
        assertEquals(1, nodeData.getTag());
        assertNotEquals(5, nodeData.getTag());
    }
}