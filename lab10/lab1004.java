/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab10;

/**
 *
 * @author kitti
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Lane extends JPanel {
    private int x = 0;
    private int speed;
    private Timer timer = new Timer(20, e -> {
        x += speed;
        if (x > getWidth()) {
            x = -50;
        }
        repaint();
    });

    public Lane(int speed) {
        this.speed = speed;
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        timer.start();
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int y = getHeight() - 4;
        g.setColor(Color.BLACK);
        g.fillOval(x + 10, y - 10, 10, 10);
        g.fillOval(x + 30, y - 10, 10, 10);
        g.setColor(Color.GREEN);
        g.fillRect(x, y - 20, 50, 10);
        g.setColor(Color.RED);
        Polygon p = new Polygon();
        p.addPoint(x + 10, y - 20);
        p.addPoint(x + 20, y - 30);
        p.addPoint(x + 30, y - 30);
        p.addPoint(x + 40, y - 20);
        g.fillPolygon(p);
    }
}

public class lab1004 extends JFrame {
    private Lane[] lanes = new Lane[4];
    private JTextField[] fields = new JTextField[4];

    public lab1004() {
        int[] init = {10, 20, 30, 40};
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        JPanel track = new JPanel(new GridLayout(4, 1));

        for (int i = 0; i < 4; i++) {
            final int idx = i;
            lanes[i] = new Lane(init[i] / 10);
            fields[i] = new JTextField(String.valueOf(init[i]), 4);
            fields[i].addActionListener(e -> apply(idx));
            fields[i].addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    apply(idx);
                }
            });
            top.add(new JLabel("Car " + (i + 1) + ":"));
            top.add(fields[i]);
            track.add(lanes[i]);
        }

        add(top, BorderLayout.NORTH);
        add(track, BorderLayout.CENTER);
    }

    private void apply(int i) {
        try {
            int v = Integer.parseInt(fields[i].getText().trim());
            lanes[i].setSpeed(Math.max(1, v / 10));
        } catch (NumberFormatException ex) {
            fields[i].setText("10");
            lanes[i].setSpeed(1);
        }
    }

    public static void main(String[] args) {
        lab1004 frame = new lab1004();
        frame.setTitle("Exercise18_17");
        frame.setSize(480, 280);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}