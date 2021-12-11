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

    public GraphGUI(MenuBar menuBar) {
        this.menuBar = menuBar;
        this.addMouseListener(this);
        isEnabled = false;
//        radioButtonState="1";
//        algorithms.load(menuBar.getFilename());
        this.addMouseListener(this);
//        this.menuBar.getContentPane().setBackground(new Color(69, 5, 111));
        this.setBackground(Color.GRAY);
        this.setMaximumSize(new Dimension(1000,1000));
        setLayout(null);

    }
    public DirectedWeightedGraphAlgorithms getAlgorithms(){return algorithms;}

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        DirectedWeightedGraph graph = algorithms.getGraph();
        GeoLocation g = new GeoLocationImpl(e.getX(), e.getY(), 0);
        NodeData n = new NodeDataImpl(g, 0);
        a.add(n);
        graph.addNode(n);
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
    public void p(Graphics g) {
        
    }

    public void paintGraph(Graphics g) {
        super.paintComponent(g);
        Graphics2D G = (Graphics2D) g;
        g.setColor(new Color(234, 26, 171));

        Iterator<NodeData> iter = algorithms.getGraph().nodeIter();
        while (iter.hasNext()) {
            NodeData n = iter.next();
            g.fillOval((int) n.getLocation().x(), (int) n.getLocation().y(), 20, 20);
            G.drawString(""+n.getKey(),(int)n.getLocation().x(),(int)n.getLocation().y());
        }

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
//        new GraphGUI();
    }
}
