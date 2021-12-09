package api.GUI;

import api.DirectedWeightedGraphImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NodeDrawer extends JFrame implements ActionListener {
    private boolean enabled;
    private JButton drawNode;
    private int x, y;
    private myPanel panel;
    private DirectedWeightedGraphImpl graph;
    public NodeDrawer(DirectedWeightedGraphImpl g, myPanel panel, int x, int y){
        super(("Add Node"));
        this.x=x;
        this.y=y;
        this.graph = g;
        this.panel = panel;

    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
    public void setEnabled(){
        this.enabled=true;
    }
    public void setDisabled(){
        this.enabled=false;
    }
}
