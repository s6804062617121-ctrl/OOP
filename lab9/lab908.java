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
import java.util.Random;
import javax.swing.*;

class Bird {
    private double y;
    private double speed;
    private double angle;

    public Bird(double y, double speed, double angle) {
        this.y = y;
        this.speed = speed;
        this.angle = angle;
    }

    public double getY() {
        return y;
    }

    public double flightTime() {
        double uy = speed * Math.sin(Math.toRadians(angle));
        return (uy + Math.sqrt(uy * uy + 20 * y)) / 10;
    }

    public double xAt(double t) {
        return speed * Math.cos(Math.toRadians(angle)) * t;
    }

    public double heightAt(double t) {
        return y + speed * Math.sin(Math.toRadians(angle)) * t - 5 * t * t;
    }

    public double landingX() {
        return xAt(flightTime());
    }
}

class Pig {
    private double x;
    private double y;

    public Pig(Random r) {
        x = 60 + r.nextInt(441);
        y = r.nextInt(21);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}

class GamePanel extends JPanel {
    private static final int ORIGIN_X = 250;
    private static final int GROUND_Y = 330;

    private Random random = new Random();
    private Pig pig = new Pig(random);
    private double birdHeight = 0;
    private int[] px = new int[0];
    private int[] py = new int[0];

    public GamePanel() {
        setPreferredSize(new Dimension(800, 400));
    }

    public Pig getPig() {
        return pig;
    }

    public void newPig() {
        pig = new Pig(random);
        px = new int[0];
        py = new int[0];
        repaint();
    }

    public void setBirdHeight(double h) {
        birdHeight = h;
        repaint();
    }

    public void showShot(Bird bird) {
        birdHeight = bird.getY();
        double total = bird.flightTime();
        int n = 80;
        px = new int[n + 1];
        py = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            double t = total * i / n;
            px[i] = ORIGIN_X + (int) bird.xAt(t);
            py[i] = GROUND_Y - (int) bird.heightAt(t);
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setPaint(new GradientPaint(0, 0, new Color(150, 190, 220), 0, GROUND_Y, new Color(225, 235, 240)));
        g2.fillRect(0, 0, getWidth(), GROUND_Y);

        g2.setColor(new Color(120, 135, 150));
        g2.fillRect(120, 200, 50, 130);
        g2.fillRect(190, 220, 40, 110);
        g2.fillRect(400, 170, 70, 160);
        g2.fillRect(560, 210, 60, 120);
        g2.fillRect(660, 190, 50, 140);

        g2.setColor(new Color(70, 100, 120));
        g2.fillRect(0, GROUND_Y, getWidth(), getHeight() - GROUND_Y);

        int pigX = ORIGIN_X + (int) pig.getX();
        int pigY = GROUND_Y - (int) pig.getY();
        g2.setColor(new Color(90, 190, 70));
        g2.fillOval(pigX - 20, pigY - 40, 40, 40);
        g2.setColor(Color.BLACK);
        g2.fillOval(pigX - 9, pigY - 28, 6, 6);
        g2.fillOval(pigX + 3, pigY - 28, 6, 6);
        g2.setColor(new Color(60, 150, 50));
        g2.fillOval(pigX - 8, pigY - 20, 16, 10);

        if (px.length > 1) {
            g2.setColor(Color.DARK_GRAY);
            g2.drawPolyline(px, py, px.length);
        }

        int birdY = GROUND_Y - (int) birdHeight;
        g2.setColor(Color.RED);
        g2.fillOval(ORIGIN_X - 18, birdY - 36, 36, 36);
        g2.setColor(Color.WHITE);
        g2.fillOval(ORIGIN_X + 2, birdY - 26, 10, 10);
        g2.setColor(Color.BLACK);
        g2.fillOval(ORIGIN_X + 6, birdY - 23, 4, 4);
        g2.setColor(Color.ORANGE);
        g2.fillPolygon(new int[]{ORIGIN_X + 14, ORIGIN_X + 26, ORIGIN_X + 14},
                new int[]{birdY - 20, birdY - 15, birdY - 10}, 3);
    }
}

public class lab908 extends JFrame {
    private GamePanel panel = new GamePanel();
    private JTextField jtfScore = new JTextField(6);
    private JTextField jtfY = new JTextField(10);
    private JTextField jtfSpeed = new JTextField(10);
    private JTextField jtfAngle = new JTextField(10);
    private JButton jbtOk = new JButton("OK");
    private int score = 0;

    public lab908() {
        jtfScore.setEditable(false);
        jtfScore.setText("0");

        JLabel title = new JLabel("SCENE 1: At Tokyo");
        title.setFont(new Font("SansSerif", Font.PLAIN, 22));

        JPanel scorePanel = new JPanel(new FlowLayout());
        scorePanel.add(new JLabel("SCORE"));
        scorePanel.add(jtfScore);

        JPanel top = new JPanel(new BorderLayout());
        top.add(title, BorderLayout.WEST);
        top.add(scorePanel, BorderLayout.EAST);

        JPanel inputs = new JPanel(new GridLayout(3, 3, 5, 5));
        inputs.add(new JLabel("Bird Position in y-axis"));
        inputs.add(jtfY);
        inputs.add(new JLabel("m"));
        inputs.add(new JLabel("Shooting speed"));
        inputs.add(jtfSpeed);
        inputs.add(new JLabel("m/s"));
        inputs.add(new JLabel("Angle"));
        inputs.add(jtfAngle);
        inputs.add(new JLabel("degree"));

        JPanel okPanel = new JPanel();
        okPanel.add(jbtOk);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(inputs, BorderLayout.CENTER);
        bottom.add(okPanel, BorderLayout.SOUTH);

        add(top, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        jbtOk.addActionListener(e -> shoot());
    }

    private void shoot() {
        try {
            double h = Double.parseDouble(jtfY.getText().trim());
            double speed = Double.parseDouble(jtfSpeed.getText().trim());
            double angle = Double.parseDouble(jtfAngle.getText().trim());
            if (h < 0 || speed <= 0 || angle <= 0 || angle >= 90) {
                throw new NumberFormatException();
            }

            Bird bird = new Bird(h, speed, angle);
            panel.showShot(bird);

            double landing = bird.landingX();
            Pig pig = panel.getPig();
            boolean hit = Math.abs(landing - pig.getX()) <= 20 && Math.abs(0 - pig.getY()) <= 20;

            String msg = String.format("Landing point: (%.0f, 0)%nPig at: (%.0f, %.0f)%n", landing, pig.getX(), pig.getY());
            if (hit) {
                score += 100;
                jtfScore.setText(String.valueOf(score));
                msg += "HIT! +100";
            } else {
                msg += "MISS";
            }
            JOptionPane.showMessageDialog(this, msg);
            panel.newPig();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter y >= 0, speed > 0 and angle between 0 and 90", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        lab908 frame = new lab908();
        frame.setTitle("AngryBirds");
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}