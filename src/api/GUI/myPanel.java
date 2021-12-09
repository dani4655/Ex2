package api.GUI;

import api.DirectedWeightedGraphImpl;
import api.EdgeData;
import api.NodeData;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.LinkedList;

public class myPanel extends JPanel {
    DirectedWeightedGraphImpl graph;

    public myPanel(DirectedWeightedGraphImpl graph) {
        this.graph=graph;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Iterator<NodeData> i = graph.nodeIter();
        while (i.hasNext()){
            g.setColor(Color.black);
            g.setFont(new Font("David",Font.ITALIC,12));
            g.fillOval((int) i.next().getLocation().x(), (int) i.next().getLocation().y(),5,5);
            g.drawString(""+i.next().getKey(), (int) (i.next().getLocation().x()+3), (int) (i.next().getLocation().y()+3));
        }
        Iterator<EdgeData> e = graph.edgeIter();
        while (e.hasNext()){
            EdgeData ed=e.next();
            int x1=(int) graph.getNode(ed.getSrc()).getLocation().x(),x2= (int) graph.getNode(ed.getDest()).getLocation().x();
            int x[]={x1,x2};
            int y1=(int) graph.getNode(ed.getSrc()).getLocation().y(),y2= (int) graph.getNode(ed.getDest()).getLocation().y();
            int y[]={y1,y2};
            g.setColor(Color.DARK_GRAY);
            g.setFont(new Font("David",Font.ITALIC,12));
            g.drawPolyline(x,y, 2);
            g.drawString(""+ed.getWeight(),(int)((x1+x2)/2),(int)((y1+y2)/2));
        }
    }

    public void drawNodesData(Graphics g){
        Iterator<NodeData> i = graph.nodeIter();
        while (i.hasNext()){
            NodeData n=i.next();
            g.setColor(Color.black);
            g.fillOval((int) n.getLocation().x(), (int) n.getLocation().y(),5,5);
            g.setFont(new Font("David",Font.ITALIC,12));
            g.drawString(""+n.getKey(), (int) (n.getLocation().x()+3), (int) (n.getLocation().y()+3));
        }
    }
    public void drawEdgesData(Graphics g){
        Iterator<EdgeData> e = graph.edgeIter();
        while (e.hasNext()){
            EdgeData ed=e.next();
            int x1=(int) graph.getNode(ed.getSrc()).getLocation().x(),x2= (int) graph.getNode(ed.getDest()).getLocation().x();
            int x[]={x1,x2};
            int y1=(int) graph.getNode(ed.getSrc()).getLocation().y(),y2= (int) graph.getNode(ed.getDest()).getLocation().y();
            int y[]={y1,y2};
            g.setColor(Color.DARK_GRAY);
            g.setFont(new Font("David",Font.ITALIC,12));
            g.drawPolyline(x,y, 2);
            g.drawString(""+ed.getWeight(),(int)((x1+x2)/2),(int)((y1+y2)/2));
        }
    }

}
