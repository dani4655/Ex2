package api.GUI;

import api.DirectedWeightedGraphImpl;
import api.NodeDataImpl;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Canvas extends JPanel implements MouseListener {

    private GUI frame;
    DirectedWeightedGraphImpl graph;

    public Canvas(GUI frame){
        this.frame=frame;
        graph=new DirectedWeightedGraphImpl();
        this.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        double x= e.getX();
        double y=e.getY();
        this.paintComponent(this.getGraphics());
    }

    @Override
    public void mousePressed(MouseEvent e) {

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


}
