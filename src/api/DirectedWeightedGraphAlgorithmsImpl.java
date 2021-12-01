package api;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
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


    @Override
    public boolean isConnected() {
        int num = graph.nodeSize();
        if (num == 0)
            return true;
        LinkedList<Integer> queue = new LinkedList<Integer>();
        int v = graph.nodeIter().next().getKey();
        int color[] = new int[num]; //0 = white, 1 = gray, 2 = black
            if (color[v] == 0) {
                color[v] = 1; //gray
                queue.add(v);
                BFS(queue, color);
        }
        for (int i = 0; i < num; i++) {
            if (color[i] == 0)
                return false;
        }
        return true;
    }

    private boolean BFS(LinkedList<Integer> queue, int[] color) {
        if (queue.isEmpty())
            return true;
        int v = queue.poll();
        Iterator<EdgeData> ei = graph.edgeIter(v);
        while (ei.hasNext()) {
            int dest = ei.next().getDest();
            if (color[dest] == 0) {
                color[dest] = 1; //gray
                queue.add(dest);
            }
        }
        color[v] = 3; //black
        return BFS(queue, color);
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
