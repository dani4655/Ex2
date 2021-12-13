package api.Gui;

import api.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Buttons implements ActionListener {
    private MenuBar menuBar;

    public Buttons(MenuBar menuBar) {
        this.menuBar = menuBar;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String s = menuBar.textField.getText();
        String b = e.getActionCommand();
        // Algo Graph:
//        if (menuBar.load.isSelected())
//            menuBar.label.setText("Enter file name");
        if (menuBar.save.isSelected())
            menuBar.label.setText("Enter file name");
        if (menuBar.shortestPathDist.isSelected())
            menuBar.label.setText("Enter source and destination (x,y)");
        if (menuBar.shortestPath.isSelected())
            menuBar.label.setText("Enter source and destination (x,y)");
        if (menuBar.tsp.isSelected())
            menuBar.label.setText("Enter list of nodes");
//        if (menuBar.addNode.isSelected())
//            menuBar.label.setText("Enter node id");
        if (menuBar.connect.isSelected())
            menuBar.label.setText("Enter two node ids and weight (x,y,w)");
        if (menuBar.getNode.isSelected())
            menuBar.label.setText("Enter node id");
        if (menuBar.getEdge.isSelected())
            menuBar.label.setText("Enter two node ids (x,y)");
        if (menuBar.removeNode.isSelected())
            menuBar.label.setText("Enter node id");
        if (menuBar.removeEdge.isSelected())
            menuBar.label.setText("Enter two node ids (x,y)");
        //addNode
        if (menuBar.addNode.isSelected()){
            menuBar.gui.setChange(true);
        }
        else
            menuBar.gui.setChange(false);
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
            menuBar.gui.paintGraph(menuBar.gui.getGraphics());
//            else
//                JOptionPane.showMessageDialog(null, "Graph have no center");
        }
        //load
        if (e.getSource() == menuBar.load) {
            menuBar.fileChooser.setCurrentDirectory(new File("data"));
            int res = menuBar.fileChooser.showOpenDialog(null);
            if (res == JFileChooser.APPROVE_OPTION) {
                String f = menuBar.fileChooser.getSelectedFile().getAbsolutePath();
                if (menuBar.gui.getAlgorithms().load(f)) {
                    menuBar.gui.paintGraph(menuBar.gui.getGraphics());
                    JOptionPane.showMessageDialog(null, "You have load the file from: \"" + f + "\"");
                }
            }
        }
        if (e.getSource() == menuBar.enterButton) {
            //load:
/*            if (menuBar.load.isSelected()) {
                if (menuBar.gui.getAlgorithms().load(s)) {
                    JOptionPane.showMessageDialog(null, "You have load the file: \"" + s + "\"");
                    menuBar.gui.paintGraph(menuBar.gui.getGraphics());
                } else
                    JOptionPane.showConfirmDialog(null, menuBar.icon);
//                JOptionPane.showMessageDialog(null, "load failed.");
            }*/
            //save:
            if (menuBar.save.isSelected()) {
                if (menuBar.gui.getAlgorithms().save(s))
                    JOptionPane.showMessageDialog(null, "You have saved the file: \"" + s + "\"");
                else
                    JOptionPane.showMessageDialog(null, "load failed.");
            }
            //shortestPathDist
            if (menuBar.shortestPathDist.isSelected()) {
                int src, dest;
                try {
                    String sr[] = s.split(",");
                    src = Integer.parseInt(sr[0]);
                    dest = Integer.parseInt(sr[1]);
                    JOptionPane.showMessageDialog(null, "The shortest path dist is: "
                            + menuBar.gui.getAlgorithms().shortestPathDist(src, dest));

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Bad input, enter 'src,dest' or nodes ids that included in the graph");
                }
            }
            //shortestPath
            if (menuBar.shortestPath.isSelected()) {
                List<NodeData> nodelist;
                List<String> stringList = new ArrayList<>();
                int src, dest;
                try {
                    String sr[] = s.split(",");
                    src = Integer.parseInt(sr[0]);
                    dest = Integer.parseInt(sr[1]);
                    nodelist = menuBar.gui.getAlgorithms().shortestPath(src, dest);
                    for (int i = 0; i < nodelist.size(); i++) {
                        if (i == nodelist.size() - 1) {
                            stringList.add(nodelist.get(i).getKey() + "");
                            continue;
                        }
                        stringList.add(nodelist.get(i).getKey() + "->");

                    }
                    JOptionPane.showMessageDialog(null, "The shortest path is: "
                            + stringList);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Bad input, enter 'src,dest' or nodes ids that included in the graph");
                }
            }
            //tsp:
            if (menuBar.tsp.isSelected()) {
                List<NodeData> list = new ArrayList<>();
                List<String> stringList = new ArrayList<>();
                /*JOptionPane.showMessageDialog(null,
                        "Enter list of cities (e.g: 0,5,7,8,9,16");*/
                try {
                    String[] sr;
                    sr = s.split(",");
                    for (int i = 0; i < sr.length; i++) {
                        list.add(menuBar.gui.getAlgorithms().getGraph().getNode(Integer.parseInt(sr[i])));
                    }
                    list = menuBar.gui.getAlgorithms().tsp(list);
                    for (int i = 0; i < list.size(); i++) {
                        if (i == list.size() - 1) {
                            stringList.add(list.get(i).getKey() + "");
                            continue;
                        }
                        stringList.add(list.get(i).getKey() + "->");

                    }
                    JOptionPane.showMessageDialog(null, "Traveler salesman path is: "
                            + stringList);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Bad input, enter 'a1,a2,...,an' or nodes ids that included in the graph");
                }
            }
            //getNode
            if (menuBar.getNode.isSelected()) {
                try {
                    int x = Integer.parseInt(s);
                    JOptionPane.showMessageDialog(null, "Node " + x +
                            " " + menuBar.gui.getAlgorithms().getGraph().getNode(x));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Bad input, enter 'node id'(e.g: 5) or nodes ids that included in the graph");
                }
            }
            //getEdge
            if (menuBar.getEdge.isSelected()) {
                try {
                    String sr[] = s.split(",");
                    int src, dest;
                    src = Integer.parseInt(sr[0]);
                    dest = Integer.parseInt(sr[1]);
                    JOptionPane.showMessageDialog(null, "Edge " +
                            "" + menuBar.gui.getAlgorithms().getGraph().getEdge(src, dest));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Bad input, enter 'source,destination' or nodes ids that included in the graph");
                }
            }
            //addNode
/*            if (menuBar.addNode.isSelected()){
                try{
                    String sr[]= s.split(",");
                    GeoLocation geoLocation = new GeoLocationImpl(Double.parseDouble(sr[0]),Double.parseDouble(sr[1]),0);
                    int id = menuBar.gui.getAlgorithms().getGraph().nodeSize()+1;
                    NodeData nodeData = new NodeDataImpl(geoLocation,id);
                    menuBar.gui.getAlgorithms().getGraph().addNode(nodeData);
                    JOptionPane.showMessageDialog(null, "Node " +id+
                            " was added successfully to the graph");
                    menuBar.gui.paintGraph(menuBar.gui.getGraphics());
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null,
                            "Bad input, enter 'source,destination' or nodes ids that included in the graph");
                }
            }*/
            //connect
            if (menuBar.connect.isSelected()) {
                try {
                    String sr[] = s.split(",");
                    int src, dest;
                    double weight;
                    src = Integer.parseInt(sr[0]);
                    dest = Integer.parseInt(sr[1]);
                    weight = Double.parseDouble(sr[2]);
                    menuBar.gui.getAlgorithms().getGraph().connect(src,dest, weight);
                    menuBar.gui.paintGraph(menuBar.gui.getGraphics());
                    JOptionPane.showMessageDialog(null,"Connected from "+src+" to "+dest+" and weights"+weight );
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Bad input, enter 'source,destination' or nodes ids that included in the graph");
                }
            }
            //remove node
            if (menuBar.removeNode.isSelected()){
                try {
                    int x = Integer.parseInt(s);
                    NodeData a = menuBar.gui.getAlgorithms().getGraph().removeNode(x);
                    menuBar.gui.paintGraph(menuBar.gui.getGraphics());
                    JOptionPane.showMessageDialog(null, "Node " + x +
                            "" + a +" was removed successfully ");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Bad input, enter 'node id'(e.g: 5) or nodes ids that included in the graph");
                }
            }
            //remove edge
            if (menuBar.removeEdge.isSelected()){
                try {
                    String sr[] = s.split(",");
                    int src, dest;
                    src = Integer.parseInt(sr[0]);
                    dest = Integer.parseInt(sr[1]);
                    EdgeData a = menuBar.gui.getAlgorithms().getGraph().removeEdge(src,dest);
                    menuBar.gui.paintGraph(menuBar.gui.getGraphics());
                    JOptionPane.showMessageDialog(null,"The edge "+src+" to "+dest+""
                            +a+" was removed successfully");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Bad input, enter 'source,destination' or nodes ids that included in the graph");
                }
            }

        }

    }
}
