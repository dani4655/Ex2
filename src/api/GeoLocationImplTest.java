package api;

import static org.junit.jupiter.api.Assertions.*;

class GeoLocationImplTest {
    GeoLocationImpl p1= new GeoLocationImpl(2.2,1,5);
    GeoLocationImpl p2= new GeoLocationImpl(3.5,22,20);

    @org.junit.jupiter.api.Test
    void x() {
        assertEquals(p1.x(),2.2);
        assertEquals(p2.x(),3.5);
    }

    @org.junit.jupiter.api.Test
    void y() {
        assertEquals(p1.y(),1);
        assertEquals(p2.y(),22);
    }

    @org.junit.jupiter.api.Test
    void z() {
        assertEquals(p1.z(),5);
        assertEquals(p2.z(),20);
    }

    @org.junit.jupiter.api.Test
    void distance() {
        assertEquals(p1.distance(p2),25.839698140651723);
        assertEquals(p1.distance(p1),0);
        assertEquals(p2.distance(p2),0);
    }
}