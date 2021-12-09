package api.GUIi;
import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MenuBar extends JFrame {
    private GraphGUI graphGUI;
    private Buttons buttons;


    JMenuBar menuBar;
    JPanel AlgoGraphPanel;
    JPanel graphPanel;
    JTextField textField;
    JButton button;
    JLabel label;
    JPanel menuPanel, file;

    // ____________________________
    //Graph:
    JButton getNode;
    JButton getEdge;
    JButton addNode;
    JButton connect;
    JButton removeNode;
    JButton removeEdge;
    JButton nodeSize;
    JButton edgeSize;
    JButton getMC;
    // ____________________________
    // ____________________________
    //Algo Graph:
    JButton isConnected;
    JButton shortestPathDist;
    JButton shortestPath;
    JButton center;
    JButton tsp;
    JButton save;
    JButton load;
    // ____________________________
    JMenuItem paint;

    public MenuBar() {
        buttons = new Buttons(this);
        menuBar = new JMenuBar();
        textField = new JTextField();
        label = new JLabel();
        button = new JButton();

        this.setJMenuBar(menuBar);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(getToolkit().getScreenSize());
        setLayout(null);
//        this.setLayout(new FlowLayout());

//        Paint = new JMenu("Paint");
//        paint = new JMenuItem("paint");
//        menuBar.add(Paint);
//        Paint.add(paint);

        //Algo Graph:

        isConnected = new JButton("isConnected");
        shortestPathDist = new JButton("shortestPathDist");
        shortestPath = new JButton("shortestPath");
        center = new JButton("center");
        tsp = new JButton("tsp");
        save = new JButton("Save");
        load = new JButton("Load");

//        menuBar.add(AlgoGraphMenu);
//        AlgoGraphMenu.add(isConnected);
//        AlgoGraphMenu.add(shortestPathDist);
//        AlgoGraphMenu.add(shortestPath);
//        AlgoGraphMenu.add(center);
//        AlgoGraphMenu.add(tsp);
//        AlgoGraphMenu.add(save);
//        AlgoGraphMenu.add(load);

        //Graph:
        getNode = new JButton("GetNode");
        getEdge = new JButton("GetEdge");
        addNode = new JButton("AddNode");
        connect = new JButton("Connect");
        removeNode = new JButton("RemoveNode");
        removeEdge = new JButton("RemoveEdge");
        nodeSize = new JButton("NodeSize");
        edgeSize = new JButton("EdgeSize");
        getMC = new JButton("GetMC");

        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
        menuPanel = new JPanel();
        menuPanel.setAlignmentX(TOP_ALIGNMENT);
        menuPanel.setMaximumSize(new Dimension(800,300));
        menu();
        add(menuPanel);
//        menuBar.add(graphMenu);
//        graphMenu.add(getNode);
//        graphMenu.add(getEdge);
//        graphMenu.add(addNode);
//        graphMenu.add(connect);
//        graphMenu.add(removeNode);
//        graphMenu.add(removeEdge);
//        graphMenu.add(nodeSize);
//        graphMenu.add(edgeSize);
//        graphMenu.add(getMC);

//        setComponents();
/*        isConnected.addActionListener(this);
        load.addActionListener(this);
        save.addActionListener(this);
        shortestPath.addActionListener(this);
        shortestPathDist.addActionListener(this);
        button.addActionListener(this);*/

    }
    public void menu() {
        menuPanel.setLayout(new GridLayout(5,5));
        // ---------------------------------
        // graphPanel
        graphPanel = new JPanel();
        graphPanel.setLayout(new GridLayout(1,2));
        graphPanel.setBounds(5,5,5,5);

        getNode.addActionListener(buttons);
        graphPanel.add(getNode);
        getEdge.addActionListener(buttons);
        graphPanel.add(getEdge);
        addNode.addActionListener(buttons);
        graphPanel.add(addNode);
        connect.addActionListener(buttons);
        graphPanel.add(connect);
        removeNode.addActionListener(buttons);
        graphPanel.add(removeNode);
        removeEdge.addActionListener(buttons);
        graphPanel.add(removeEdge);
        nodeSize.addActionListener(buttons);
        graphPanel.add(nodeSize);
        edgeSize.addActionListener(buttons);
        graphPanel.add(edgeSize);
        getMC.addActionListener(buttons);
        graphPanel.add(getMC);
        menuPanel.add(graphPanel);
        // ---------------------------------

        // ---------------------------------
        // Algo Panel
        AlgoGraphPanel= new JPanel();
        AlgoGraphPanel.setLayout(new GridLayout(1,2));
        AlgoGraphPanel.setBounds(5,5,5,5);
        isConnected.addActionListener(buttons);
        AlgoGraphPanel.add(isConnected);
        shortestPathDist.addActionListener(buttons);
        AlgoGraphPanel.add(shortestPathDist);
        shortestPath.addActionListener(buttons);
        AlgoGraphPanel.add(shortestPath);
        center.addActionListener(buttons);
        AlgoGraphPanel.add(center);
        tsp.addActionListener(buttons);
        AlgoGraphPanel.add(tsp);
        save.addActionListener(buttons);
        AlgoGraphPanel.add(save);
        load.addActionListener(buttons);
        AlgoGraphPanel.add(load);
        menuPanel.add(AlgoGraphPanel);
        // ---------------------------------

        file = new JPanel();
        file.setLayout(new GridLayout(1,1));
        file.setBounds(5,5,5,5);



    }
    public void setComponents() {
        graphGUI = new GraphGUI();
        graphGUI.setAlignmentX(BOTTOM_ALIGNMENT);
        graphGUI.setSize(1,1);
        add(graphGUI);
    }

    public void text() {
        textField.setBounds(50, 100, 250, 20);
        label = new JLabel();
        label.setBounds(50, 50, 222, 20);
        button = new JButton("Enter");
        button.setBounds(50, 150, 95, 30);
/*        button.addActionListener(this);*/
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && textField.equals("hi")) {
                    System.out.println("hi");
                }
            }
        });

        this.add(label);
        this.add(textField);
        this.add(button);
    }
/*
    @Override
    public void actionPerformed(ActionEvent e) {
        String s = textField.getText();
        if (e.getSource() == button) {
            System.out.println(s);
        }
        if (e.getSource() == paint) {

        }
        if (e.getSource() == load) {
            text();
            label.setText("Enter file name");
        }
        if (e.getSource() == save) {
            text();

            label.setText("Enter file name");
        }
        if (e.getSource() == shortestPathDist) {
            text();
            label.setText("Enter source and destination (x,y)");
        }
        if (e.getSource() == shortestPath) {
            text();
            label.setText("Enter source and destination (x,y)");
        }
    }*/


    public static void main(String[] args) {
        new MenuBar();
    }
}
