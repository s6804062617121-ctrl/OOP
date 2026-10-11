/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab11;

import java.awt.*;
import javax.swing.*;

public class lab1106 extends JFrame {
    private JTextField[] speedFields = new JTextField[4];
    private Lane[] lanes = new Lane[4];

    public lab1106 () {
        setTitle("Exercise18_17");
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout());
        int[] defaults = {10, 20, 30, 40};
        for (int i = 0; i < 4; i++) {
            top.add(new JLabel("Car " + (i + 1) + ":"));
            speedFields[i] = new JTextField(String.valueOf(defaults[i]), 4);
            top.add(speedFields[i]);
        }
        add(top, BorderLayout.NORTH);

        JPanel track = new JPanel(new GridLayout(4, 1));
        for (int i = 0; i < 4; i++) {
            lanes[i] = new Lane(speedFields[i]);
            track.add(lanes[i]);
        }
        add(track, BorderLayout.CENTER);

        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        for (int i = 0; i < 4; i++) {
            lanes[i].startCar();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new lab1106 ());
    }
}

class Lane extends JPanel {
    private volatile int x = 0;
    private JTextField speedField;

    Lane(JTextField speedField) {
        this.speedField = speedField;
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    }

    class CarThread extends Thread {
        public void run() {
            while (true) {
                x += 2;
                if (x > getWidth()) {
                    x = -60;
                }
                repaint();
                try {
                    Thread.sleep(getDelay());
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    private int getDelay() {
        int speed;
        try {
            speed = Integer.parseInt(speedField.getText().trim());
        } catch (NumberFormatException e) {
            speed = 10;
        }
        if (speed < 1) {
            speed = 1;
        }
        return Math.max(1, 400 / speed);
    }

    public void startCar() {
        CarThread t = new CarThread();
        t.setDaemon(true);
        t.start();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int y = getHeight() - 15;
        int cx = x;

        g.setColor(Color.BLACK);
        g.fillOval(cx + 10, y - 10, 10, 10);
        g.fillOval(cx + 30, y - 10, 10, 10);

        g.setColor(Color.GREEN);
        g.fillRect(cx, y - 20, 50, 10);

        g.setColor(Color.RED);
        Polygon p = new Polygon();
        p.addPoint(cx + 10, y - 20);
        p.addPoint(cx + 20, y - 30);
        p.addPoint(cx + 30, y - 30);
        p.addPoint(cx + 40, y - 20);
        g.fillPolygon(p);
    }
}