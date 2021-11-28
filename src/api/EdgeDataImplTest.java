package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeDataImplTest {
    EdgeDataImpl a = new EdgeDataImpl(5, 3, 1,4,"my name is edge" );
    EdgeDataImpl b = new EdgeDataImpl(4, 5, 7,2,"my name is edge 2" );

    @Test
    void getSrc() {
        assertEquals(a.getSrc(), 5);
        assertEquals(b.getSrc(), 4);

    }

    @Test
    void getDest() {
        assertEquals(a.getDest(), 3);
        assertEquals(b.getDest(), 5);

    }

    @Test
    void getWeight() {
        assertEquals(a.getWeight(), 1);
        assertEquals(b.getWeight(), 7);

    }

    @Test
    void getInfo() {
        assertEquals(a.getInfo(), "my name is edge");
        assertEquals(b.getInfo(), "my name is edge 2");


    }

    @Test
    void setInfo() {
        a.setInfo("test1");
        b.setInfo("test2");
        assertEquals(a.getInfo(), "test1");
        assertEquals(b.getInfo(), "test2");

    }

    @Test
    void getTag() {
        assertEquals(a.getTag(),4);
        assertEquals(b.getTag(),2);

    }

    @Test
    void setTag() {
        a.setTag(3);
        b.setTag(4);
        assertEquals(a.getTag(),3);
        assertEquals(b.getTag(),4);
    }
}