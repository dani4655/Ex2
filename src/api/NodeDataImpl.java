package api;

public class NodeDataImpl implements NodeData {
    private GeoLocation geoLocation;
    private int node_id; // key
    private int tag;  // color
    private double node_weight;
    private String info; // meta data

    public NodeDataImpl(GeoLocation geoLocation, int node_id, int tag, double node_weight, String remark) {
        this.geoLocation = geoLocation;
        this.node_id = node_id;
        this.tag = tag;
        this.node_weight = node_weight;
        this.info = remark;
    }

    public NodeDataImpl(GeoLocation g , int id) {
        this.node_id = id;
        this.node_weight = 0;
        this.tag = 0;
        this.info = "";
        this.geoLocation = g;
    }

    /*    */

    /**
     * this function returns new deep copy of NodeDataImpl
     *
     * @return NodeDataImpl
     *//*
//    public NodeDataImpl copy(){
//        NodeDataImpl nNodeData = new NodeDataImpl(geoLocation, node_id, tag,node_weight, remark);
//        return nNodeData;
//    }*/
    @Override
    public int getKey() {
        return this.node_id;
    }

    @Override
    public GeoLocation getLocation() {
        if (this.geoLocation == null)
            return null;
        return this.geoLocation;
    }

    @Override
    public void setLocation(GeoLocation p) {
        this.geoLocation = p;
    }

    @Override
    public double getWeight() {
        return node_weight;
    }

    @Override
    public void setWeight(double w) {
        this.node_weight = w;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }

    @Override
    public String toString() {
        return
                "geoLocation=" + geoLocation +
                ", node_id=" + node_id +
                ", tag=" + tag +
                ", node_weight=" + node_weight +
                ", info='" + info + '\'' +
                '}' + "\n";
    }
}
