package api;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/*import com.google.gson.*;
import com.google.gson.reflect.TypeToken;*/

public class DirectedWeightedGraphAlgorithmsImpl implements DirectedWeightedGraphAlgorithms {
    private DirectedWeightedGraph graph;

    public DirectedWeightedGraphAlgorithmsImpl(DirectedWeightedGraph graph) {
        this.graph = graph;
    }

    public DirectedWeightedGraphAlgorithmsImpl() {
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
            node = copy(node.getKey());
            nDWG.addNode(node);
        }
        while (edgeDataIterator.hasNext()) {
            EdgeData edgei = edgeDataIterator.next();
            int src = edgei.getSrc(), dst = edgei.getDest();
            double weight = edgei.getWeight();
            nDWG.connect(src, dst, weight);
            nDWG.getEdge(src, dst).setInfo(edgei.getInfo());
            nDWG.getEdge(src, dst).setTag(edgei.getTag());
        }
        return nDWG;
    }

    /**
     * makes a deep copy from and id of given nodeData
     *
     * @param id
     * @return nodeDataImpl
     */
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
        color[v] = 1; //gray
        queue.add(v);
        BFS(queue, color, this.graph);

        for (int i = 0; i < num; i++) {
            if (color[i] == 0)
                return false;
        }
        if (!transpose())
            return false;

        return true;
    }

    private boolean BFS(LinkedList<Integer> queue, int[] color, DirectedWeightedGraph g) {
        if (queue.isEmpty())
            return true;
        int v = queue.poll();
        Iterator<EdgeData> ei = g.edgeIter(v);
        while (ei.hasNext()) {
            int dest = ei.next().getDest();
            if (color[dest] == 0) {
                color[dest] = 1; //gray
                queue.add(dest);
            }
        }
        color[v] = 3; //black
        return BFS(queue, color, this.graph);
    }

    private boolean transpose() {
        Iterator<EdgeData> ei = graph.edgeIter();
        Iterator<NodeData> nd = graph.nodeIter();
        DirectedWeightedGraph g = new DirectedWeightedGraphImpl();
        while (nd.hasNext()) { //new graph
            NodeData n = nd.next();
            g.addNode(n);
        }
        while (ei.hasNext()) { //transpose edges (connect)
            EdgeData e = ei.next();
            g.connect(e.getDest(), e.getSrc(), e.getWeight());
        }
        LinkedList<Integer> q = new LinkedList<Integer>();
        int v = g.nodeIter().next().getKey();
        int color[] = new int[g.nodeSize()]; //0 = white, 1 = gray, 2 = black
        color[v] = 1; //gray
        q.add(v);
        BFS(q, color, g);
        for (int i = 0; i < g.nodeSize(); i++) {
            if (color[i] == 0)
                return false;
        }
        return true;
    }

    private DirectedWeightedGraph dijkstra(NodeData src) {
        DirectedWeightedGraph ng = this.copy();
        maxValue(ng);
        ng.getNode(src.getKey()).setWeight(0);
        PriorityQueue<NodeData> q = new PriorityQueue<>();
        Iterator<NodeData> iter = ng.nodeIter();
        while (!iter.hasNext()){
            q.add(iter.next());
        }
        while (!q.isEmpty()) {
            NodeData cn = q.poll();
            if (q.peek() != null && q.peek().getWeight() < ng.getNode(src.getKey()).getWeight()) {
                q.add(ng.getNode(src.getKey()));
                cn = q.poll();
            }
            Iterator<EdgeData> e = ng.edgeIter(cn.getKey());
            while (e.hasNext()){
                relax(e.next(),ng);
            }
        }
        return ng;
    }

    /**this function is the relax function for dijkstra algorithm
     *
     * @param e
     */
    private void relax(EdgeData e, DirectedWeightedGraph ng) {
        if (ng.getNode(e.getDest()).getWeight()>ng.getNode(e.getSrc()).getWeight()+e.getWeight()){
            ng.getNode(e.getDest()).setWeight(ng.getNode(e.getSrc()).getWeight()+e.getWeight());
            ng.getNode(e.getDest()).setTag(e.getSrc());
        }
    }

    /**this function init the graph to be max int for each vertex in the graph
     *
     */
    private void maxValue(DirectedWeightedGraph ng) {
        Iterator<NodeData> iter = ng.nodeIter();
        while (!iter.hasNext()){
            ng.nodeIter().next().setWeight(Integer.MAX_VALUE);
            ng.nodeIter().next().setTag(-1);
        }
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        DirectedWeightedGraph ng = dijkstra(this.graph.getNode(src));
        int a = dest;
        double length = 0;
        if (this.graph.getNode(src)==null || this.graph.getNode(dest)==null)
            return -1;
        while(a!=src){
            int b=a;
            a=ng.getNode(a).getTag();
            length += ng.getEdge(b,a).getWeight();
        }
        return length;


    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public NodeData center() { //O(n^3)
/*        if (!isConnected())
            return null;
        int inf = Integer.MAX_VALUE;
        int N = graph.nodeSize();
        int E = graph.edgeSize();
        int[][] mat = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                mat[i][j] = inf;
                if (i == j)
                    mat[i][j] = 0;
            }
        }
        for (int i = 0; i < E; i++) {
            int x = graph.edgeIter().next().getSrc();
            int y = graph.edgeIter().next().getDest();
            mat[x][y] = 1;
        }
        for (int k = 0; k < N; k++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    int a = inf;
                    if (mat[i][k] != inf || mat[k][j] != inf)
                        a = mat[i][k] + mat[k][j];
                    mat[i][j] = Math.min(mat[i][j], a);
                }
            }
        }
        int min = mat[0][0];
        int src = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (mat[i][j] < min) {
                    min = mat[i][j];
                    src = i;
                }
            }
        }
        return graph.getNode(src);*/
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
   /*     try {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(DirectedWeightedGraphImpl.class, new toJson());
            Gson gson = builder.create();

            FileReader reader = new FileReader(file);
            this.graph = gson.fromJson(reader, DirectedWeightedGraphImpl.class);
            System.out.println(this.graph.toString());
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }*/
    }


}
