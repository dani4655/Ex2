package api;

public class NodeDataImpl implements NodeData {
    int node_id;
    GeoLocation geoLocation;

    @Override
    public int getKey() {
        return this.node_id;
    }

    @Override
    public GeoLocation getLocation() {
        return geoLocation;
    }

    @Override
    public void setLocation(GeoLocation p) {
        this.geoLocation = p;
    }

    @Override
    public double getWeight() {
        return 0;
    }

    @Override
    public void setWeight(double w) {

    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void setInfo(String s) {

    }

    @Override
    public int getTag() {
        return 0;
    }

    @Override
    public void setTag(int t) {

    }
}
