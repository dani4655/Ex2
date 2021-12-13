package api.Gui;

import api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.LinkedList;

public class GraphGUI extends JPanel implements MouseListener {

    private MenuBar menuBar;
    private static boolean isEnabled;

    protected static DirectedWeightedGraphAlgorithms algorithms = new DirectedWeightedGraphAlgorithmsImpl();
    private LinkedList<NodeData> a = new LinkedList<>();
    private static boolean change;


    public GraphGUI(MenuBar menuBar) {
        this.menuBar = menuBar;
        this.addMouseListener(this);
        change = false;
        this.setBackground(new Color(28, 210, 210));
        this.setMaximumSize(new Dimension(1000, 1000));
        setLayout(null);
        algorithms.load(this.menuBar.getFilename());


    }

    public DirectedWeightedGraphAlgorithms getAlgorithms() {
        return algorithms;
    }
//    public boolean getChange() {
//        return this.change;
//    }

    public static void setChange(boolean change) {
        GraphGUI.change = change;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!change)
            return;
        Iterator<NodeData> iter = algorithms.getGraph().nodeIter();
        NodeData n = iter.next();
        double xmin = n.getLocation().x();
        double ymin = n.getLocation().y();
        double xmax = n.getLocation().x();
        double ymax = n.getLocation().y();
        while (iter.hasNext()) {
            n = iter.next();
            if (n.getLocation().x() < xmin)
                xmin = n.getLocation().x();
            if (n.getLocation().y() < ymin)
                ymin = n.getLocation().y();
            if (n.getLocation().x() > xmax)
                xmax = n.getLocation().x();
            if (n.getLocation().y() > ymax)
                ymax = n.getLocation().y();
        }
        double X = this.getWidth() / Math.abs(xmax - xmin) * 0.975;
        double Y = this.getWidth() / Math.abs(ymax - ymin) * 0.975;
        double x = e.getX() / X + xmin;
        double y = e.getY() / Y + ymin;

        DirectedWeightedGraph graph = algorithms.getGraph();
        GeoLocation g = new GeoLocationImpl(x, y, 0);
        int b= 0;
        for (int i = 0; i < graph.nodeSize() + 1; i++) {
            if (graph.getNode(i) == null) {
                b = i;
                break;
            }

        }
        n = new NodeDataImpl(g, b);
//        a.add(n);
        graph.addNode(n);
        paintComponent(getGraphics());

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void p(Graphics g) {

    }
    private void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h) {
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx*dx + dy*dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm*cos - ym*sin + x1;
        ym = xm*sin + ym*cos + y1;
        xm = x;

        x = xn*cos - yn*sin + x1;
        yn = xn*sin + yn*cos + y1;
        xn = x;

        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};

        g.drawLine(x1, y1, x2, y2);
        g.fillPolygon(xpoints, ypoints, 3);
    }

    public void paintComponent(Graphics g) {
        Iterator<NodeData> iter = algorithms.getGraph().nodeIter();
        NodeData n = iter.next();
        double xmin = n.getLocation().x();
        double ymin = n.getLocation().y();
        double xmax = n.getLocation().x();
        double ymax = n.getLocation().y();
        while (iter.hasNext()) {
            n = iter.next();
            if (n.getLocation().x() < xmin)
                xmin = n.getLocation().x();
            if (n.getLocation().y() < ymin)
                ymin = n.getLocation().y();
            if (n.getLocation().x() > xmax)
                xmax = n.getLocation().x();
            if (n.getLocation().y() > ymax)
                ymax = n.getLocation().y();
        }

        super.paintComponent(g);
        double X = this.getWidth() / Math.abs(xmax - xmin) * 0.975;
        double Y = this.getWidth() / Math.abs(ymax - ymin) * 0.975;
        //edges
        Iterator<EdgeData> eiter = algorithms.getGraph().edgeIter();
        while (eiter.hasNext()) {
            EdgeData e = eiter.next();
            NodeData nsrc = algorithms.getGraph().getNode(e.getSrc());
            NodeData ndest = algorithms.getGraph().getNode(e.getDest());
            int sx = (int) ((nsrc.getLocation().x() - xmin) * X) + 12;
            int sy = (int) ((nsrc.getLocation().y() - ymin) * Y) + 12;
            int dx = (int) ((ndest.getLocation().x() - xmin) * X) + 12;
            int dy = (int) ((ndest.getLocation().y() - ymin) * Y) + 12;
            g.setColor(new Color(234, 26, 171));
            drawArrowLine(g,sx, sy, dx, dy,26,5);
        }
        //nodes
        Iterator<NodeData> iterator = algorithms.getGraph().nodeIter();
        while (iterator.hasNext()) {
            n = iterator.next();
            int x = (int) ((n.getLocation().x() - xmin) * X);
            int y = (int) ((n.getLocation().y() - ymin) * Y);
            if (n.getTag() == 5) { //center
                g.setColor(new Color(17, 232, 83));
                g.fillOval(x, y, 20, 20);
                g.setColor(Color.white);
                g.drawString("" + n.getKey(), x + 5, y + 15);
                g.setColor(new Color(248, 248, 248));
            }
            else {
                g.setColor(new Color(234, 26, 171));
                g.fillOval(x, y, 20, 20);
                g.setColor(Color.white);
                g.drawString("" + n.getKey(), x + 5, y + 15);
                g.setColor(new Color(248, 248, 248));
            }
        }


    }


//    public void paint(Graphics g) {
//        super.paintComponent(g);
//        NodeData prev = null;
//        for (NodeData n : a) {
//            g.setColor(new Color(234, 26, 171));
//            g.fillOval((int) n.getLocation().x(), (int) n.getLocation().y(), 20, 20);
//            if (prev != null) {
//                Double dist = n.getLocation().distance(prev.getLocation());
//                String distS = dist.toString().substring(0, dist.toString().indexOf(".") + 2);
//                g.drawLine((int) n.getLocation().x(), (int) n.getLocation().y(), (int) prev.getLocation().x(), (int) prev.getLocation().y());
//                g.drawString(distS, (int) ((n.getLocation().x() + prev.getLocation().x()) / 2), (int) ((n.getLocation().y() + prev.getLocation().y()) / 2));
//            }
//            prev = n;
//        }
//    }


    public static void main(String[] args) {
//        new GraphGUI();
    }
}
