package api.GUIi;

import api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.LinkedList;

public class GraphGUI extends JPanel implements MouseListener {

    private MenuBar menuBar;
    private DirectedWeightedGraph graph = new DirectedWeightedGraphImpl();
    private DirectedWeightedGraphAlgorithms algorithms = new DirectedWeightedGraphAlgorithmsImpl(graph);
    private LinkedList<NodeData> a = new LinkedList<>();

    public GraphGUI() {
        this.addMouseListener(this);
    }



    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        GeoLocation g = new GeoLocationImpl(e.getX(), e.getY(), 0);
        NodeData n = new NodeDataImpl(g, 0);
        a.add(n);
        this.graph.addNode(n);
        repaint();
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

    public void paint(Graphics g) {
        super.paintComponent(g);
        NodeData prev = null;
        for (NodeData n : a) {
            g.setColor(new Color(234, 26, 171));
            g.fillOval((int) n.getLocation().x(), (int) n.getLocation().y(), 20, 20);
            if (prev != null) {
                Double dist = n.getLocation().distance(prev.getLocation());
                String distS = dist.toString().substring(0, dist.toString().indexOf(".") + 2);
                g.drawLine((int) n.getLocation().x(), (int) n.getLocation().y(), (int) prev.getLocation().x(), (int) prev.getLocation().y());
                g.drawString(distS, (int) ((n.getLocation().x() + prev.getLocation().x()) / 2), (int) ((n.getLocation().y() + prev.getLocation().y()) / 2));
            }
            prev = n;
        }
    }


    public static void main(String[] args) {
        new GraphGUI();
    }
}
