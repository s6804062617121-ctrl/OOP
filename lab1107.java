/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab11;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.*;

public class lab1107 extends JFrame {
    public lab1107 () {
        GamePanel panel = new GamePanel();
        add(panel);
        setTitle("Exercise");
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        panel.requestFocusInWindow();
        panel.startGame();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new lab1107 ());
    }
}

class GamePanel extends JPanel {
    private static final int BALLOON_R = 10;
    private static final int GUN_LEN = 30;
    private static final double BULLET_SPEED = 6;

    private Random random = new Random();
    private volatile double angle = 0;
    private volatile double balloonX = 50;
    private volatile double balloonY = 20;
    private volatile double bulletX;
    private volatile double bulletY;
    private volatile double bulletDX;
    private volatile double bulletDY;
    private volatile boolean bulletActive = false;

    GamePanel() {
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    angle = Math.max(angle - Math.toRadians(5), -Math.toRadians(80));
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    angle = Math.min(angle + Math.toRadians(5), Math.toRadians(80));
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    fire();
                }
                repaint();
            }
        });
    }

    private void fire() {
        if (bulletActive) {
            return;
        }
        int cx = getWidth() / 2;
        int by = getHeight();
        bulletX = cx + GUN_LEN * Math.sin(angle);
        bulletY = by - GUN_LEN * Math.cos(angle);
        bulletDX = BULLET_SPEED * Math.sin(angle);
        bulletDY = -BULLET_SPEED * Math.cos(angle);
        bulletActive = true;
    }

    private void newBalloon() {
        int w = Math.max(getWidth(), 100);
        int h = Math.max(getHeight(), 100);
        balloonX = BALLOON_R + random.nextInt(w - 2 * BALLOON_R);
        balloonY = BALLOON_R + random.nextInt(h / 2);
    }

    class GameThread extends Thread {
        public void run() {
            newBalloon();
            while (true) {
                if (bulletActive) {
                    bulletX += bulletDX;
                    bulletY += bulletDY;
                    double dx = bulletX - balloonX;
                    double dy = bulletY - balloonY;
                    if (Math.sqrt(dx * dx + dy * dy) <= BALLOON_R) {
                        bulletActive = false;
                        newBalloon();
                    } else if (bulletX < 0 || bulletX > getWidth() || bulletY < 0) {
                        bulletActive = false;
                    }
                }
                repaint();
                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    public void startGame() {
        GameThread t = new GameThread();
        t.setDaemon(true);
        t.start();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.drawOval((int) (balloonX - BALLOON_R), (int) (balloonY - BALLOON_R), 2 * BALLOON_R, 2 * BALLOON_R);

        int cx = getWidth() / 2;
        int by = getHeight();
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(cx, by, cx + (int) (GUN_LEN * Math.sin(angle)), by - (int) (GUN_LEN * Math.cos(angle)));

        if (bulletActive) {
            g2.fillOval((int) bulletX - 3, (int) bulletY - 3, 6, 6);
        }
    }
}