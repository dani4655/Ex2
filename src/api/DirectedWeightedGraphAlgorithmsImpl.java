package api;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

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
        HashMap<Integer, Integer> color = new HashMap<>(); //0 = white, 1 = gray, 2 = black
        Iterator<NodeData> iter = graph.nodeIter();
        while (iter.hasNext()) { //add all nodes and set to 0
            int n = iter.next().getKey();
            color.put(n,0);
        }
        color.remove(v);
        color.put(v,1); //gray
        queue.add(v);
        BFS(queue, color, this.graph);

        for (int i: color.keySet()) {
            if (color.get(i) == 0)
                return false;
        }
        if (!transpose())
            return false;

        return true;
    }

    private boolean BFS(LinkedList<Integer> queue, HashMap<Integer, Integer> color, DirectedWeightedGraph g) {
        if (queue.isEmpty())
            return true;
        int v = queue.poll();
        Iterator<EdgeData> ei = g.edgeIter(v);
        while (ei.hasNext()) { //check neighbors
            int dest = ei.next().getDest();
            if (color.get(dest) == 0) {
                color.remove(dest);
                color.put(dest,1) ; //gray
                queue.add(dest);
            }
        }
        color.remove(v);
        color.put(v,3); //black
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
        HashMap<Integer, Integer> color = new HashMap<>(); //0 = white, 1 = gray, 2 = black
        Iterator<NodeData> iter = g.nodeIter(); //add all nodes and set to 0
        while (iter.hasNext()) {
            int n = iter.next().getKey();
            color.put(n,0);
        }
        color.remove(v);
        color.put(v,1); //gray
        q.add(v);
        BFS(q, color, g);
        for (int i: color.keySet()) {
            if (color.get(i) == 0)
                return false;
        }
        return true;
    }

    private DirectedWeightedGraph dijkstra(NodeData src) {
        DirectedWeightedGraph ng = this.copy();
        maxValue(ng);
        ng.getNode(src.getKey()).setWeight(0);
        LinkedList<NodeData> q = new LinkedList<NodeData>();
        Iterator<NodeData> iter = ng.nodeIter();
        while (iter.hasNext()) {
            NodeData n = iter.next();
            q.add(n);
        }
        while (!q.isEmpty()) {
            NodeData cn = q.poll();
            if (q.peek() != null && q.peek().getWeight() < ng.getNode(src.getKey()).getWeight()) {
                q.add(ng.getNode(src.getKey()));
                cn = q.poll();
            }
            Iterator<EdgeData> e = ng.edgeIter(cn.getKey());
            while (e.hasNext()) {
                relax(e.next(), ng);
            }
        }
        return ng;
    }

    /**
     * this function is the relax function for dijkstra algorithm
     *
     * @param e
     */
    private void relax(EdgeData e, DirectedWeightedGraph ng) {
        if (ng.getNode(e.getDest()).getWeight() > ng.getNode(e.getSrc()).getWeight() + e.getWeight()) {
            ng.getNode(e.getDest()).setWeight(ng.getNode(e.getSrc()).getWeight() + e.getWeight());
            ng.getNode(e.getDest()).setTag(e.getSrc());
        }
    }

    /**
     * this function init the graph to be max int for each vertex in the graph
     */
    private void maxValue(DirectedWeightedGraph ng) {
        Iterator<NodeData> iter = ng.nodeIter();
        while (iter.hasNext()) {
            NodeData n = iter.next();
            n.setWeight(Integer.MAX_VALUE);
            n.setTag(-1);

        }

    }

    @Override
    public double shortestPathDist(int src, int dest) {
        if (src == dest)
            return 0.0;
        DirectedWeightedGraph ng = dijkstra(this.graph.getNode(src));
        int a = dest;
        double length = 0;
        while (a != src) {
            int b = a;
            a = ng.getNode(a).getTag();
            length += ng.getEdge(a, b).getWeight();
        }
        return length;
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        List<NodeData> list = new LinkedList<>();
        if (src == dest) {
            list.add(graph.getNode(src));
            return list;
        }
        DirectedWeightedGraph ng = dijkstra(this.graph.getNode(src));
        int a = dest;
        list.add(ng.getNode(dest));
        while (a != src) {
            a = ng.getNode(a).getTag();
            list.add(ng.getNode(a));
        }
        Collections.reverse(list);
        return list;
    }

    @Override
    public NodeData center() { //O(n^3)
        if (!isConnected())
            return null;
        int N = graph.nodeSize();
        double mat[][] = mat();

        double[] src = new double[N];
        for (int i = 0; i < N; i++) {
            double max = mat[i][0];
            src[i] = max;
            for (int j = 0; j < N; j++) {
                if (i == j)
                    continue;
                if (mat[i][j] > max) {
                    max = mat[i][j];
                    src[i] = max;
                }
            }
        }
        double min = src[0];
        int ans = 0;
        for (int i = 1; i < N; i++) {
            if (src[i] < min) {
                min = src[i];
                ans = i;
            }
        }
        return graph.getNode(ans);
    }


    private double[][] mat() {
        double inf = Double.POSITIVE_INFINITY;
        int N = graph.nodeSize();
        double[][] mat = new double[N][N];
        for (int i = 0; i < N; i++) { //make all INF
            for (int j = 0; j < N; j++) {
                mat[i][j] = inf;
                if (i == j)
                    mat[i][j] = 0;
            }
        }
        Iterator<EdgeData> ei = graph.edgeIter();
        while (ei.hasNext()) {
            EdgeData e = ei.next();
            int x = e.getSrc();
            int y = e.getDest();
            mat[x][y] = graph.getEdge(x, y).getWeight();
        }
        for (int k = 0; k < N; k++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    double a = inf;
                    if (mat[i][k] != inf || mat[k][j] != inf)
                        a = mat[i][k] + mat[k][j];
                    mat[i][j] = Math.min(mat[i][j], a);
                }
            }
        }
        return mat;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        NodeData s = cities.get(0);
        boolean visit[] = new boolean[cities.size()];
        double cost = 0;
        for (int i = 1; i < cities.size(); i++) {

        }
        return null;
    }

    @Override
    public boolean save(String file) {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        String str = builder.create().toJson(this.graph);
        try {
            PrintWriter p = new PrintWriter(new File(file));
            p.write(str);
            p.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean load(String file) {
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(DirectedWeightedGraphImpl.class, new toJson());
            Gson gson = builder.create();

            FileReader reader = new FileReader(file);
            this.graph = gson.fromJson(reader, DirectedWeightedGraphImpl.class);
//            System.out.println(this.graph.toString());
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }


}
