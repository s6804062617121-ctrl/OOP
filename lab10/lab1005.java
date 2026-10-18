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
import java.util.Random;
import javax.swing.*;

class GamePanel extends JPanel {
    private static final int BALLOON_R = 12;
    private static final int GUN_LEN = 30;

    private Random random = new Random();
    private int balloonX;
    private int balloonY;
    private double angle = 0;
    private boolean flying = false;
    private double bx;
    private double by;
    private double dx;
    private double dy;

    private Timer timer = new Timer(10, e -> step());

    public GamePanel() {
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
                    angle = Math.max(angle - Math.toRadians(3), -Math.toRadians(80));
                    repaint();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    angle = Math.min(angle + Math.toRadians(3), Math.toRadians(80));
                    repaint();
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    fire();
                }
            }
        });
        timer.start();
    }

    private int gunX() {
        return getWidth() / 2;
    }

    private int gunY() {
        return getHeight() - 5;
    }

    private void newBalloon() {
        int w = Math.max(getWidth(), 100);
        int h = Math.max(getHeight(), 100);
        balloonX = BALLOON_R + random.nextInt(w - 2 * BALLOON_R);
        balloonY = BALLOON_R + random.nextInt(h / 2);
    }

    private void fire() {
        if (flying) {
            return;
        }
        flying = true;
        bx = gunX() + GUN_LEN * Math.sin(angle);
        by = gunY() - GUN_LEN * Math.cos(angle);
        dx = 4 * Math.sin(angle);
        dy = -4 * Math.cos(angle);
    }

    private void step() {
        if (balloonX == 0 && balloonY == 0 && getWidth() > 0) {
            newBalloon();
        }
        if (flying) {
            bx += dx;
            by += dy;
            double d = Math.hypot(bx - balloonX, by - balloonY);
            if (d <= BALLOON_R) {
                flying = false;
                newBalloon();
            } else if (bx < 0 || bx > getWidth() || by < 0 || by > getHeight()) {
                flying = false;
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.drawOval(balloonX - BALLOON_R, balloonY - BALLOON_R, 2 * BALLOON_R, 2 * BALLOON_R);

        int gx = gunX();
        int gy = gunY();
        g.drawLine(gx, gy, (int) (gx + GUN_LEN * Math.sin(angle)), (int) (gy - GUN_LEN * Math.cos(angle)));

        if (flying) {
            g.fillOval((int) bx - 3, (int) by - 3, 6, 6);
        }
    }
}

public class lab1005 extends JFrame {
    private GamePanel panel = new GamePanel();

    public lab1005() {
        add(panel);
    }

    public static void main(String[] args) {
        lab1005 frame = new lab1005();
        frame.setTitle("Exercise");
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.panel.requestFocusInWindow();
    }
}