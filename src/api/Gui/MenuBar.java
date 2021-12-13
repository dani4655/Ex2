package api.Gui;

import api.GeoLocation;
import api.GeoLocationImpl;
import api.NodeData;
import api.NodeDataImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;

public class MenuBar extends JFrame {
    protected GraphGUI gui;
    private Buttons buttons;
    private String filename;


    JMenuBar menuBar;
    JPanel AlgoGraphPanel;
    JPanel graphPanel;
    JTextField textField;
    JButton enterButton;
    JLabel label;
    JPanel menuPanel, paintPanel, file;
    ImageIcon icon;

    // ____________________________
    //Graph:
    JRadioButton getNode;
    JRadioButton getEdge;
    JRadioButton addNode;
    JRadioButton connect;
    JRadioButton removeNode;
    JRadioButton removeEdge;
    JButton nodeSize;
    JButton edgeSize;
    JButton getMC;
    // ____________________________
    // ____________________________
    //Algo Graph:
    JButton isConnected;
    JRadioButton shortestPathDist;
    JRadioButton shortestPath;
    JButton center;
    JRadioButton tsp;
    JRadioButton save;
    JButton load;
    // ____________________________
    JFileChooser fileChooser;



    public MenuBar(String filename) {
        this.filename = filename;
        gui = new GraphGUI(this);
        buttons = new Buttons(this);
        menuBar = new JMenuBar();
        textField = new JTextField();
        label = new JLabel();
        enterButton = new JButton("Enter");
        icon = new ImageIcon("data/myIcon.jpeg");
        ButtonGroup buttonGroup = new ButtonGroup();
        fileChooser = new JFileChooser();

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
        center = new JButton("center");
        shortestPathDist = new JRadioButton("shortestPathDist");
        shortestPath = new JRadioButton("shortestPath");
        tsp = new JRadioButton("tsp");
        save = new JRadioButton("Save");
        load = new JButton("Load");
        buttonGroup.add(shortestPathDist);
        buttonGroup.add(shortestPath);
        buttonGroup.add(tsp);
        buttonGroup.add(save);
//        buttonGroup.add(load);


        //Graph:
        getNode = new JRadioButton("GetNode");
        getEdge = new JRadioButton("GetEdge");
        addNode = new JRadioButton("AddNode");
        connect = new JRadioButton("Connect");
        removeNode = new JRadioButton("RemoveNode");
        removeEdge = new JRadioButton("RemoveEdge");
        nodeSize = new JButton("NodeSize");
        edgeSize = new JButton("EdgeSize");
        getMC = new JButton("GetMC");
        buttonGroup.add(getNode);
        buttonGroup.add(getEdge);
        buttonGroup.add(addNode);
        buttonGroup.add(connect);
        buttonGroup.add(removeNode);
        buttonGroup.add(removeEdge);


        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
        menuPanel = new JPanel();
        menuPanel.setBounds(0, 0, 250, 250);
        paintPanel = new JPanel();
        paintPanel.setBounds(250, 0, 250, 250);
//        menuPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.setMaximumSize(new Dimension(800, 300));
        setComponents();
        menu();
        add(menuPanel);
        text();

    }

    public String getFilename() {
        return filename;
    }

    public void menu() {
        menuPanel.setLayout(new GridLayout(5, 3));
        // ---------------------------------
        // graphPanel
        graphPanel = new JPanel();
        graphPanel.setLayout(new GridLayout(1, 2));
        graphPanel.setBounds(5, 5, 5, 5);
//        graphPanel.setBackground(new Color(56, 13, 91));
//        AlgoGraphPanel.setBackground(new Color(77, 24, 110));
        paintPanel.setBackground(new Color(17, 185, 215));


        nodeSize.addActionListener(buttons);
        graphPanel.add(nodeSize);
        edgeSize.addActionListener(buttons);
        graphPanel.add(edgeSize);
        getMC.addActionListener(buttons);
        graphPanel.add(getMC);
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
        menuPanel.add(graphPanel);
        // ---------------------------------

        // ---------------------------------
        // Algo Panel
        AlgoGraphPanel = new JPanel();
        AlgoGraphPanel.setLayout(new GridLayout(1, 2));
        AlgoGraphPanel.setBounds(5, 5, 5, 5);
        load.addActionListener(buttons);
        AlgoGraphPanel.add(load);
        isConnected.addActionListener(buttons);
        AlgoGraphPanel.add(isConnected);
        center.addActionListener(buttons);
        AlgoGraphPanel.add(center);
        shortestPathDist.addActionListener(buttons);
        AlgoGraphPanel.add(shortestPathDist);
        shortestPath.addActionListener(buttons);
        AlgoGraphPanel.add(shortestPath);
        tsp.addActionListener(buttons);
        AlgoGraphPanel.add(tsp);
        save.addActionListener(buttons);
        AlgoGraphPanel.add(save);
        menuPanel.add(AlgoGraphPanel);

        // ---------------------------------

        enterButton.addActionListener(buttons);
        menuPanel.add(enterButton);
        file = new JPanel();
        file.setLayout(new GridLayout(1, 1));
        file.setBounds(5, 5, 5, 5);


    }

    public void setComponents() {
        gui.setAlignmentX(BOTTOM_ALIGNMENT);
        gui.setSize(1, 1);
        add(gui);
    }

    public void text() {
        textField.setBounds(50, 100, 250, 20);
        label = new JLabel();
        label.setBounds(50, 50, 222, 20);
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && textField.equals("hi")) {
                    System.out.println("hi");
                }
            }
        });

        menuPanel.add(label);
        menuPanel.add(textField);
    }


    public static void main(String[] args) {
        new MenuBar("data/G1.json");
    }
}
