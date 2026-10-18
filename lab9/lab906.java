/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab9;

/**
 *
 * @author kitti
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class HangmanPanel extends JPanel {
    private int offset = 0;

    public HangmanPanel() {
        setFocusable(true);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                requestFocusInWindow();
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    moveLeft();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    moveRight();
                }
            }
        });
    }

    public void moveLeft() {
        offset = Math.max(offset - 10, -80);
        repaint();
    }

    public void moveRight() {
        offset = Math.min(offset + 10, 60);
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 300);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawArc(20, 220, 80, 40, 0, 180);
        g.drawLine(20 + 40, 220, 20 + 40, 20);
        g.drawLine(20 + 40, 20, 20 + 40 + 100, 20);

        g.translate(offset, 0);

        g.drawLine(20 + 40 + 100, 20, 20 + 40 + 100, 40);
        int radius = 20;
        g.drawOval(20 + 40 + 100 - radius, 40, 2 * radius, 2 * radius);

        g.drawLine(20 + 40 + 100 - (int) (radius * Math.cos(Math.toRadians(45))),
                40 + radius + (int) (radius * Math.sin(Math.toRadians(45))),
                20 + 40 + 100 - 60, 40 + radius + 60);

        g.drawLine(20 + 40 + 100 + (int) (radius * Math.cos(Math.toRadians(45))),
                40 + radius + (int) (radius * Math.sin(Math.toRadians(45))),
                20 + 40 + 100 + 60, 40 + radius + 60);

        g.drawLine(20 + 40 + 100, 40 + 2 * radius,
                20 + 40 + 100, 40 + radius + 80);

        g.drawLine(20 + 40 + 100, 40 + radius + 80,
                20 + 40 + 100 - 40, 40 + radius + 80 + 40);

        g.drawLine(20 + 40 + 100, 40 + radius + 80,
                20 + 40 + 100 + 40, 40 + radius + 80 + 40);

        g.translate(-offset, 0);
    }
}

public class lab906 extends JFrame {
    private HangmanPanel panel = new HangmanPanel();

    public lab906() {
        add(panel);
    }

    public static void main(String[] args) {
        lab906 frame = new lab906();
        frame.setTitle("Hangman");
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.panel.requestFocusInWindow();
    }
}