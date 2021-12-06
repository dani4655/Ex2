package api;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class toJson implements JsonDeserializer<DirectedWeightedGraph> {

    @Override
    public DirectedWeightedGraph deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        DirectedWeightedGraph g = new DirectedWeightedGraphImpl();
        JsonArray edges = jsonObject.get("Edges").getAsJsonArray();
        Iterator<JsonElement> edgeiter = edges.iterator();
        JsonArray nodes = jsonObject.get("Nodes").getAsJsonArray();
        Iterator<JsonElement> nodeiter = nodes.iterator();
        //Nodes:
        while (nodeiter.hasNext()) {
            JsonObject val = nodeiter.next().getAsJsonObject();
            int id = val.get("id").getAsInt();
            //pos:
            String geo = val.get("pos").getAsString();
            String[] location;
            location = geo.split(",");
            double x = Double.parseDouble(location[0]);
            double y = Double.parseDouble(location[1]);
            double z = Double.parseDouble(location[2]);
            GeoLocation ng = new GeoLocationImpl(x, y, z);
            NodeData n = new NodeDataImpl(ng, id);
            g.addNode(n);
        }
        //Edges:
        while (edgeiter.hasNext()) {
            JsonObject val = edgeiter.next().getAsJsonObject();
            int src = val.get("src").getAsInt();
            double w = val.get("w").getAsDouble();
            int dest = val.get("dest").getAsInt();
            g.connect(src, dest, w);
        }
        return g;
    }
}
