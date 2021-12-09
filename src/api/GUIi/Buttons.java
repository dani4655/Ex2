package api.GUIi;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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

    public void text() {
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = menuBar.textField.getText();
        String b = e.getActionCommand();
        if (e.getSource() == menuBar.button) {
            System.out.println(s);
        }
        if (e.getSource() == menuBar.paint) {

        }
        if (b.equals("Load")) {
            this.menuBar.load.addActionListener(this);
            text();
            menuBar.label.setText("Enter file name");

        }
        if (b.equals("Save")) {
            text();
            menuBar.label.setText("Enter file name");
        }
        if (b.equals("shortestPathDist")) {
            text();
            menuBar.label.setText("Enter source and destination (x,y)");
        }
        if (b.equals("shortestPath")) {
            text();
            menuBar.label.setText("Enter source and destination (x,y)");
        }
    }
}
