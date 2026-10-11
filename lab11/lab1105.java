/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab11;

import java.awt.*;
import javax.swing.*;

public class lab1105 extends JFrame {
    public lab1105() {
        CarPanel panel = new CarPanel();
        add(panel);
        setTitle("Exercise");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        panel.startCar();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new lab1105());
    }
}

class CarPanel extends JPanel {
    private volatile int x = 0;

    class CarThread extends Thread {
        public void run() {
            while (true) {
                x += 2;
                if (x > getWidth()) {
                    x = -60;
                }
                repaint();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    public void startCar() {
        CarThread t = new CarThread();
        t.setDaemon(true);
        t.start();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int y = getHeight() - 30;
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