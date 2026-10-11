/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab11;

import java.awt.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.*;

public class lab1104 extends JFrame {
    public lab1104() {
        ClockPanel panel = new ClockPanel();
        add(panel);
        setTitle("Clock Animation");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        panel.startClock();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new lab1104());
    }
}

class ClockPanel extends JPanel {
    private volatile int hour;
    private volatile int minute;
    private volatile int second;

    class ClockThread extends Thread {
        public void run() {
            while (true) {
                Calendar c = new GregorianCalendar();
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);
                second = c.get(Calendar.SECOND);
                repaint();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    public void startClock() {
        ClockThread t = new ClockThread();
        t.setDaemon(true);
        t.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        int radius = (int) (Math.min(getWidth(), getHeight()) * 0.8 / 2);

        g.setColor(Color.BLACK);
        g.drawOval(cx - radius, cy - radius, 2 * radius, 2 * radius);
        g.drawString("12", cx - 6, cy - radius + 15);
        g.drawString("3", cx + radius - 12, cy + 4);
        g.drawString("6", cx - 3, cy + radius - 5);
        g.drawString("9", cx - radius + 6, cy + 4);

        int sLen = (int) (radius * 0.8);
        int mLen = (int) (radius * 0.65);
        int hLen = (int) (radius * 0.5);

        double sAngle = second * Math.PI / 30;
        double mAngle = (minute + second / 60.0) * Math.PI / 30;
        double hAngle = ((hour % 12) + minute / 60.0) * Math.PI / 6;

        g.setColor(Color.RED);
        g.drawLine(cx, cy, cx + (int) (sLen * Math.sin(sAngle)), cy - (int) (sLen * Math.cos(sAngle)));
        g.setColor(Color.BLUE);
        g.drawLine(cx, cy, cx + (int) (mLen * Math.sin(mAngle)), cy - (int) (mLen * Math.cos(mAngle)));
        g.setColor(Color.GREEN);
        g.drawLine(cx, cy, cx + (int) (hLen * Math.sin(hAngle)), cy - (int) (hLen * Math.cos(hAngle)));
    }
}