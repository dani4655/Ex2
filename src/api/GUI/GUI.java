package api.GUI;

import api.DirectedWeightedGraphImpl;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private JPanel setM, json, nodes, edges, algo;
    private DirectedWeightedGraphImpl graph;
    private Button button;

    public GUI(/*DirectedWeightedGraphImpl graph*/){
        super();
        this.graph=graph;
        setTitle("GUI");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(dim);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        JMenu menu= new JMenu("menu");
        JMenuBar menuBar = new JMenuBar();
        JMenuItem item = new JMenuItem("item");
        menu.add(menuBar);
        menuBar.add(menu);
        button= new Button("mashu");
        button.setBounds(200,400,100,50);
        getContentPane().add(button);
        getContentPane().add(menuBar);

    }

    private void menu(){
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
        setM= new JPanel();
        json= new JPanel();
        nodes=new JPanel();
        edges = new JPanel();
        algo = new JPanel();
        setM.setAlignmentX(LEFT_ALIGNMENT);


    }

    public static void main(String[] args) {
        new GUI();
    }
}
