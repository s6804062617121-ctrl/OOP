package cardbattle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * BackgroundArtist (grass battlefield, Home/Win/Lose cover art)

 */
public class BackgroundArtist {

    /** Grass battlefield used as the background of the 4-lane board */
    public static void drawGrassField(Graphics2D g, int w, int h) {
        BufferedImage img = ImageLoader.load("grass_bg.jpg");
        if (img == null) {
            img = ImageLoader.load("grass_bg.png");
        }
        if (img != null) {
            g.drawImage(img, 0, 0, w, h, null);
            return;
        }
        //  draw a green       
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setPaint(new GradientPaint(0, 0, new Color(140, 200, 90), 0, h, new Color(95, 155, 65)));
        g.fillRect(0, 0, w, h);

        g.setColor(new Color(70, 130, 50));
        Random r = new Random(7); // fixed seed = same grass pattern every time
        int tufts = (w * h) / 4000;
        for (int i = 0; i < tufts; i++) {
            int gx = r.nextInt(Math.max(1, w));
            int gy = r.nextInt(Math.max(1, h));
            g.drawLine(gx, gy, gx - 3, gy - 7);
            g.drawLine(gx, gy, gx, gy - 8);
            g.drawLine(gx, gy, gx + 3, gy - 7);
        }
    }

    /**
     * Full-screen cover art, used for the Home / Win / Lose screen
     * mood: "home", "win", "lose"
     */
    public static void drawCover(Graphics2D g, int w, int h, String mood) {
        BufferedImage img = ImageLoader.load(mood + "_cover.png");
        if (img == null) {
            img = ImageLoader.load(mood + "_cover.jpg");
        }
        if (img != null) {
            g.drawImage(img, 0, 0, w, h, null);
            return;
        }

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color top;
        Color bottom;
        switch (mood) {
            case "win":
                top = new Color(255, 236, 140);
                bottom = new Color(255, 193, 90);
                break;
            case "lose":
                // lose
               
                top = new Color(180, 170, 200);
                bottom = new Color(150, 140, 180);
                break;
            default: // home
                top = new Color(160, 220, 255);
                bottom = new Color(210, 240, 255);
        }
        g.setPaint(new GradientPaint(0, 0, top, 0, h, bottom));
        g.fillRect(0, 0, w, h);

        // Grass 
        int grassH = h / 5;
        g.setColor(new Color(120, 190, 90));
        g.fillRect(0, h - grassH, w, grassH);

        if (mood.equals("win")) {
            // Sun 
            g.setColor(new Color(255, 225, 120));
            g.fillOval(w / 2 - 50, 40, 100, 100);
            g.setStroke(new BasicStroke(4f));
            for (int a = 0; a < 360; a += 30) {
                double rad = Math.toRadians(a);
                int x1 = (int) (w / 2 + Math.cos(rad) * 60);
                int y1 = (int) (90 + Math.sin(rad) * 60);
                int x2 = (int) (w / 2 + Math.cos(rad) * 82);
                int y2 = (int) (90 + Math.sin(rad) * 82);
                g.drawLine(x1, y1, x2, y2);
            }
            // Confetti
            Random r = new Random(3);
            Color[] confetti = {Color.RED, Color.BLUE, Color.YELLOW, Color.MAGENTA, Color.CYAN};
            for (int i = 0; i < 40; i++) {
                g.setColor(confetti[i % confetti.length]);
                g.fillRect(r.nextInt(Math.max(1, w)), r.nextInt(Math.max(1, h / 2)), 6, 6);
            }
        } else if (mood.equals("lose")) {
            g.setColor(new Color(70, 70, 90));
            g.fillOval(60, 50, 120, 40);
            g.fillOval(w - 180, 80, 140, 40);
        } else { // home
            g.setColor(new Color(255, 230, 140));
            g.fillOval(w - 110, 30, 70, 70);
        }
    }
}
