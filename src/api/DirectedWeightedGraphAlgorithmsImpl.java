package api;

import java.io.*;
import java.util.*;

import com.google.gson.*;

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
        int v = graph.nodeIter().next().getKey();
        DirectedWeightedGraph ng = this.copy();
        ng = dijkstra(ng.getNode(v), ng);
        Iterator<NodeData> iter = ng.nodeIter();
        while (iter.hasNext()) { //add all nodes and set to 0
            NodeData n = iter.next();
            if (n.getWeight() == Integer.MAX_VALUE)
                return false;
        }
        if (!transpose())
            return false;

        return true;
    }

/*
    private boolean BFS(LinkedList<Integer> queue, HashMap<Integer, Integer> color, DirectedWeightedGraph g) {
        if (queue.isEmpty())
            return true;
        int v = queue.poll();
        Iterator<EdgeData> ei = g.edgeIter(v);
        while (ei.hasNext()) { //check neighbors
            int dest = ei.next().getDest();
            if (color.get(dest) == 0) {
                color.remove(dest);
                color.put(dest, 1); //gray
                queue.add(dest);
            }
        }
        color.remove(v);
        color.put(v, 3); //black
        return BFS(queue, color, this.graph);
    }
*/

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
        int v = g.nodeIter().next().getKey();
        g = dijkstra(g.getNode(v), g);
        Iterator<NodeData> iter = g.nodeIter(); //add all nodes and set to 0
        while (iter.hasNext()) {
            NodeData n = iter.next();
            if (n.getWeight() == Integer.MAX_VALUE)
                return false;
        }
        return true;
    }

    private DirectedWeightedGraph dijkstra(NodeData src, DirectedWeightedGraph ng) {
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
        DirectedWeightedGraph ng = this.copy();
        ng = dijkstra(this.graph.getNode(src), ng);
        if (ng.getNode(src) == null || ng.getNode(dest) == null)
            return -1;
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
        DirectedWeightedGraph ng = this.copy();
        ng = dijkstra(this.graph.getNode(src), ng);
        int a = dest;
        list.add(ng.getNode(dest));
        while (a != src) {
            a = ng.getNode(a).getTag();
            list.add(ng.getNode(a));
        }
        Collections.reverse(list);
        int last = list.get(0).getKey();
        if (last != src)
            return null;
        return list;
    }

    @Override
    public NodeData center() { //O(n^3)
        if (!isConnected())
            return null;
        int N = graph.nodeSize();
        double mat[][] = mat(); //shortests path
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
        graph.getNode(ans).setTag(5);
        return graph.getNode(ans);
    }


    private double[][] mat() {
        double inf = Double.POSITIVE_INFINITY;
        int N = this.graph.nodeSize();
        HashMap<Integer, Integer> valToInt = new HashMap<>();
        HashMap<Integer, Integer> intToVal = new HashMap<>();
        Iterator<NodeData> iter = this.graph.nodeIter();
        int index = 0;
        while (iter.hasNext()) {
            NodeData n = iter.next();
            valToInt.put(n.getKey(), index);
            intToVal.put(index, n.getKey());
            index++;
        }
        double[][] mat = new double[N][N];
        //make all INF and zero (if node to node)
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                mat[i][j] = inf;
                if (i == j)
                    mat[i][j] = 0;
            }
        }
        //set the init weights
        Iterator<EdgeData> ei = graph.edgeIter();
        while (ei.hasNext()) {
            EdgeData e = ei.next();
            int x = valToInt.get(e.getSrc());
            int y = valToInt.get(e.getDest());
            mat[x][y] = this.graph.getEdge(e.getSrc(), e.getDest()).getWeight();
            //mat[x][y] = graph.getEdge(x, y).getWeight();
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
        int N = cities.size();
        List<NodeData> list = new LinkedList<>();
        List<NodeData> ans = new LinkedList<>();
        // Create graph (with node lis):
        DirectedWeightedGraph ng = new DirectedWeightedGraphImpl();
        for (int i = 0; i < N; i++) { //copy nodes (from list)
            ng.addNode(cities.get(i));
        }
        for (int i = 0; i < N; i++) { //connect nodes
            int k = cities.get(i).getKey();
            Iterator<EdgeData> iter = this.graph.edgeIter(k);
            while (iter.hasNext()) {
                int n = iter.next().getDest();
                if (ng.getNode(n) == null)
                    continue;
                ng.connect(k, n, graph.getEdge(k, n).getWeight());
            }
        }
        DirectedWeightedGraphAlgorithms d = new DirectedWeightedGraphAlgorithmsImpl(ng);
        if (!d.isConnected())
            return null;
        // make HashMaps for nodes:
        HashMap<Integer, Integer> valToInt = new HashMap<>();
        HashMap<Integer, Integer> intToVal = new HashMap<>();
        Iterator<NodeData> iter = ng.nodeIter();
        int i = 0;
        while (iter.hasNext()) {
            NodeData n = iter.next();
            valToInt.put(n.getKey(), i);
            intToVal.put(i, n.getKey());
            i++;
        }
        double cost = 0;
        // rec fun:
        DirectedWeightedGraphAlgorithms a = new DirectedWeightedGraphAlgorithmsImpl(ng);
        double[][] mat = matFromList(valToInt, intToVal, ng);
        double min = Double.POSITIVE_INFINITY;
        for (int j = 0; j < N; j++) {
            int s = cities.get(j).getKey();
            cost = 0;
            tsprec(a.copy(), list, N, s);
            if (list.size() == N) {
                return list;
/*                for (int k = 0; k < N - 1; k++) {
                    cost += ng.getEdge(list.get(k).getKey(), list.get(k + 1).getKey()).getWeight();
                }
                if (cost < min) {
                    min = cost;
                    for (int k = 0; k < N; k++) {
                        ans.add(list.get(k));
                    }
                }
                list.clear();*/
            }
        }
        System.out.println(cost);
        return ans;
    }

    private List<NodeData> tsprec(DirectedWeightedGraph ng, List<NodeData> list, int N, int s) {
        if (list.size() == N)
            return list;
        Iterator<EdgeData> iter = ng.edgeIter(s);
        int key = -1;
        while (iter.hasNext()) { //go over all the src edges

            EdgeData e = iter.next();
            key = e.getDest();
            if (list.contains(ng.getNode(key))) //if visited - pass
                continue;
            list.add(ng.getNode(key));
            tsprec(ng, list, N, key);
            if (list.size() == N)
                return list;
        }
        if (list.contains(ng.getNode(s))) {// remove
            list.remove(ng.getNode(s));

        }
        return list;
    }
//        if (list.size() == N)
//            return list;
///*        if (ng.getNode(s) != null)
//        ng.getNode(s).setWeight(-1.0); //set as visit*/
//        Iterator<EdgeData> iter = ng.edgeIter(s);
//        double min = Double.POSITIVE_INFINITY;
//        int key = -1;
//        while (iter.hasNext()) {
//            EdgeData e = iter.next();
//            if (list.contains(ng.getNode(e.getDest())))
//                continue;
//            if (mat[valToInt.get(s)][valToInt.get(e.getDest())] < min) {
//                if (ng.getEdge(s,e.getDest()).getTag() == -1 || e.getDest() == src) {
//                    continue;
//                }
//                min = mat[valToInt.get(s)][valToInt.get(e.getDest())]; //edge weight (s,dest)
//                key = e.getDest(); //dest ID
//            }
//        }
//        if (key == -1) { //go back
//            if (list.size() == 1 ) {
//                return list;
//            }
//            int dad = ng.getNode(s).getTag();
//
//            list.remove(ng.getNode(s));
//            ng.getNode(s).setTag(-1);
//            if (x != -1 && src != dad && ng.getEdge(s,x) != null) {
//                Iterator<EdgeData> iterator = ng.edgeIter();
//                while (iterator.hasNext()) {
//                    EdgeData e = iterator.next();
//                    if (!list.contains(ng.getNode(e.getSrc())))
//                        continue;
//                    e.setTag(0);
//                }
////                ng.getEdge(s, x).setTag(0);
//            }
//            return tsprec(ng, list, mat, N, dad, valToInt, s,src);
//        } else if (key != -1) {
//            list.add(ng.getNode(key)); //add next node to return list
//            ng.getNode(key).setTag(s); //set dad
//            ng.getEdge(s,key).setTag(-1);
//            return tsprec(ng, list, mat, N, key, valToInt, x,src);
//        }
////        int dad = ng.getNode(s).getTag();
////        if (list.size() == 1)
////            return list;
////        list.remove(ng.getNode(s));
////        ng.getNode(s).setTag(-1);
////        if (x != -1 && x != src)
////            ng.getEdge(s,x).setTag(0);
////
////        return tsprec(ng, list, mat, N, dad, valToInt, s,src);
//        return list;
//    }

    private double[][] matFromList(HashMap<Integer, Integer> valToInt, HashMap<Integer, Integer> intToVal, DirectedWeightedGraph ng) {
        double inf = Double.POSITIVE_INFINITY;
        int N = valToInt.size();
        double[][] mat = new double[N][N];
        for (int i = 0; i < N; i++) { //make all INF
            for (int j = 0; j < N; j++) {
                mat[i][j] = inf;
                if (i == j)
                    mat[i][j] = 0;
            }
        }
        for (int i = 0; i < N; i++) {
            Iterator<EdgeData> iter = ng.edgeIter(intToVal.get(i));
            while (iter.hasNext()) {
                EdgeData e = iter.next();
                int x = valToInt.get(e.getSrc());
                int y = valToInt.get(e.getDest());
                mat[x][y] = ng.getEdge(e.getSrc(), e.getDest()).getWeight();
            }
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
    public boolean save(String file) {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        try {
            PrintWriter p = new PrintWriter(new File(file));
            graphForJson g = new graphForJson(this.graph);
            p.write(builder.create().toJson(g));
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
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }


}
