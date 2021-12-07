package api;

import org.w3c.dom.Node;

import java.util.Iterator;

public class graphForJson {

    private class nodeForJson {
        private String pos;
        private int id;

        public nodeForJson(String pos, int id) {
            this.pos = pos;
            this.id = id;
        }

    }
    private class edgeForJson {
        private int src;
        private double w;
        private int dest;

        public edgeForJson(int src, double w, int dest) {
            this.src = src;
            this.w = w;
            this.dest = dest;
        }
    }
    private edgeForJson[] Edges;
    private nodeForJson[] Nodes;

    public graphForJson(DirectedWeightedGraph graph) {
        Edges = new edgeForJson[graph.edgeSize()];
        Nodes = new nodeForJson[graph.nodeSize()];
        Iterator<NodeData> nodeiter = graph.nodeIter();
        Iterator<EdgeData> edgeiter = graph.edgeIter();

        for (int i = 0; i < Edges.length; i++) {
            EdgeData e = edgeiter.next();
            Edges[i] = new edgeForJson(e.getSrc(),e.getWeight(),e.getDest());
        }
        for (int i = 0; i < Nodes.length; i++) {
            NodeData n = nodeiter.next();
            String pos = n.getLocation().x() + "," + n.getLocation().y() + "," + n.getLocation().z();
            Nodes[i] = new nodeForJson(pos,n.getKey());
        }
    }


}
