package api;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DirectedWeightedGraphAlgorithmsImpl implements DirectedWeightedGraphAlgorithms {
    private DirectedWeightedGraph graph;

    public DirectedWeightedGraphAlgorithmsImpl(DirectedWeightedGraph graph) {
        this.graph = new DirectedWeightedGraphImpl();
    }

    @Override
    public void init(DirectedWeightedGraph g) {
        this.graph = g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.graph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        Iterator<EdgeData> edgeDataIterator = graph.edgeIter();
        Iterator<NodeData> nodeDataIterator = graph.nodeIter();
        DirectedWeightedGraph nDWG = new DirectedWeightedGraphImpl();
        while (nodeDataIterator.hasNext()) {
            NodeDataImpl node = (NodeDataImpl) nodeDataIterator.next();
            node= copy(node.getKey());
            nDWG.addNode(node);
        }
        while (edgeDataIterator.hasNext()) {
            EdgeData edgei = edgeDataIterator.next();
//            edgei = copy(edgei.getSrc(), edgei.getDest());
            int src= edgei.getSrc(), dst= edgei.getDest();
            double weight = edgei.getWeight();
            nDWG.connect(src,dst,weight);
            nDWG.getEdge(src,dst).setInfo(edgei.getInfo());
            nDWG.getEdge(src,dst).setTag(edgei.getTag());
        }
        return nDWG;
    }

    private NodeDataImpl copy(int id) {
        NodeDataImpl nNodeData = new NodeDataImpl(this.graph.getNode(id).getLocation(), id, graph.getNode(id).getTag(),
                graph.getNode(id).getWeight(), graph.getNode(id).getInfo());
        return nNodeData;
    }
/*    private EdgeDataImpl copy(int src, int dst){
        EdgeDataImpl nEdgeData= new EdgeDataImpl(src, dst, graph.getEdge(src, dst).getWeight(),
                graph.getEdge(src,dst).getTag() , graph.getEdge(src,dst).getInfo());
        return nEdgeData;
    }*/


    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public NodeData center() {
        return null;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        return null;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }
}
