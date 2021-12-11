package api.Gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Buttons implements ActionListener {
    private MenuBar menuBar;

    public Buttons(MenuBar menuBar) {
        this.menuBar = menuBar;
/*        this.menuBar.isConnected.addActionListener(this);
        this.menuBar.load.addActionListener(this);
        this.menuBar.save.addActionListener(this);
        this.menuBar.shortestPath.addActionListener(this);
        this.menuBar.shortestPathDist.addActionListener(this);
        this.menuBar.button.addActionListener(this);*/
    }

/*    public void text() {
        menuBar.textField.setBounds(50, 100, 250, 20);
        menuBar.label = new JLabel();
        menuBar.label.setBounds(50, 50, 222, 20);
        menuBar.button = new JButton("Enter");
        menuBar.button.setBounds(50, 150, 95, 30);
        menuBar.button.addActionListener(this);
        menuBar.textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && menuBar.textField.equals("hi")) {
                    System.out.println("hi");
                }
            }
        });

        menuBar.add(menuBar.label);
        menuBar.add(menuBar.textField);
        menuBar.add(menuBar.button);
    }*/

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = menuBar.textField.getText();
        String b = e.getActionCommand();
        // Algo Graph:
        if (menuBar.load.isSelected())
            menuBar.label.setText("Enter file name");
        if (menuBar.save.isSelected())
            menuBar.label.setText("Enter file name");
        if (menuBar.shortestPathDist.isSelected())
            menuBar.label.setText("Enter source and destination (x,y)");
        if (menuBar.shortestPath.isSelected())
            menuBar.label.setText("Enter source and destination (x,y)");
        if (menuBar.tsp.isSelected())
            menuBar.label.setText("Enter list of nodes");
        if (menuBar.addNode.isSelected())
            menuBar.label.setText("Enter node id");
        if (menuBar.connect.isSelected())
            menuBar.label.setText("Enter two node ids (x,y)");
        if (menuBar.getNode.isSelected())
            menuBar.label.setText("Enter node id");
        if (menuBar.getEdge.isSelected())
            menuBar.label.setText("Enter two node ids (x,y)");
        if (menuBar.removeNode.isSelected())
            menuBar.label.setText("Enter node id");
        if (menuBar.removeEdge.isSelected())
            menuBar.label.setText("Enter two node ids (x,y)");
        //nodeSize
        if (e.getSource() == menuBar.nodeSize) {
            int x = menuBar.gui.getAlgorithms().getGraph().nodeSize();
            JOptionPane.showMessageDialog(null, "The graph have: \"" + x + "\"" + " nodes");
        }//edgeSize
        if (e.getSource() == menuBar.edgeSize) {
            int x = menuBar.gui.getAlgorithms().getGraph().edgeSize();
            JOptionPane.showMessageDialog(null, "The graph have: \"" + x + "\"" + " edges");
        }//getMC
        if (e.getSource() == menuBar.getMC) {
            int x = menuBar.gui.getAlgorithms().getGraph().getMC();
            JOptionPane.showMessageDialog(null, "MC: \"" + x + "\"");
        }//isConnected
        if (e.getSource() == menuBar.isConnected) {
            if (menuBar.gui.getAlgorithms().isConnected())
                JOptionPane.showMessageDialog(null, "The graph is connected!");
            else
                JOptionPane.showMessageDialog(null, "The graph is not connected!");
        }//Center:
        if (e.getSource() == menuBar.center) {
            int x = menuBar.gui.getAlgorithms().center().getKey();
            JOptionPane.showMessageDialog(null, "Graph center is node: " + x);
//            else
//                JOptionPane.showMessageDialog(null, "Graph have no center");
        }
        if (e.getSource() == menuBar.enterButton) {
            //load:
            if (menuBar.load.isSelected() && e.getSource() == menuBar.enterButton) {
                if (menuBar.gui.getAlgorithms().load(s)) {
                    JOptionPane.showMessageDialog(null, "You have load the file: \"" + s + "\"");
                    menuBar.gui.paintGraph(menuBar.gui.getGraphics());
                }
                else
                    JOptionPane.showConfirmDialog(null, menuBar.icon);
//                JOptionPane.showMessageDialog(null, "load failed.");
            }
            //save:
            if (menuBar.save.isSelected()) {
                if (menuBar.gui.getAlgorithms().save(s))
                    JOptionPane.showMessageDialog(null, "You have saved the file: \"" + s + "\"");
                else
                    JOptionPane.showMessageDialog(null, "load failed.");
            }
            //tsp:
            /*if (menuBar.tsp.isSelected() &&) {
                if (menuBar.gui.getAlgorithms().tsp(menuBar.textField.))
                    JOptionPane.showMessageDialog(null, "You have saved the file: \"" + s + "\"");
                else
                    JOptionPane.showMessageDialog(null, "load failed.");
            }*/


        }
    }
}
