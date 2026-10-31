package cardbattle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * CardArtist: draws character art 
 */
public class CardArtist {

    /** Draw a cat face */
    public static void drawCat(Graphics2D g, int x, int y, int size, Color fur, int tier) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int cx = x + size / 2;
        int cy = y + size / 2;
        int r = size / 2 - 4;

        // Triangle ears
        g.setColor(fur);
        g.fillPolygon(
                new int[]{cx - r + 4, cx - r + 18, cx - r - 2},
                new int[]{cy - r + 10, cy - r + 10, cy - r - 14}, 3);
        g.fillPolygon(
                new int[]{cx + r - 4, cx + r - 18, cx + r + 2},
                new int[]{cy - r + 10, cy - r + 10, cy - r - 14}, 3);

        // Round head
        g.fillOval(cx - r, cy - r, r * 2, r * 2);

        // Eyes
        g.setColor(Color.WHITE);
        g.fillOval(cx - r / 2 - 8, cy - 6, 14, 14);
        g.fillOval(cx + r / 2 - 6, cy - 6, 14, 14);
        g.setColor(Color.BLACK);
        g.fillOval(cx - r / 2 - 3, cy - 2, 6, 6);
        g.fillOval(cx + r / 2 - 1, cy - 2, 6, 6);

        // Pink nose
        g.setColor(new Color(255, 150, 180));
        g.fillPolygon(new int[]{cx - 4, cx + 4, cx}, new int[]{cy + 8, cy + 8, cy + 14}, 3);

        // Whiskers
        g.setColor(Color.DARK_GRAY);
        g.drawLine(cx - r + 2, cy + 10, cx - r - 14, cy + 6);
        g.drawLine(cx - r + 2, cy + 16, cx - r - 14, cy + 18);
        g.drawLine(cx + r - 2, cy + 10, cx + r + 14, cy + 6);
        g.drawLine(cx + r - 2, cy + 16, cx + r + 14, cy + 18);

        switch (tier) {
            case 2: // red fighter band
                g.setColor(Color.RED);
                g.fillRect(cx - r, cy + r - 6, r * 2, 8);
                break;
            case 3: // black ninja mask
                g.setColor(Color.BLACK);
                g.fillRect(cx - r, cy - 10, r * 2, 12);
                break;
            case 4: // knight's helmet
                g.setColor(Color.LIGHT_GRAY);
                g.fillArc(cx - r, cy - r - 6, r * 2, r, 0, 180);
                g.setColor(Color.GRAY);
                g.drawArc(cx - r, cy - r - 6, r * 2, r, 0, 180);
                break;
            case 5: // golden crown
                g.setColor(new Color(255, 215, 0));
                g.fillPolygon(
                        new int[]{cx - 16, cx - 8, cx, cx + 8, cx + 16},
                        new int[]{cy - r + 2, cy - r - 10, cy - r + 2, cy - r - 10, cy - r + 2}, 5);
                break;
            default:
                break; // tier 1: no extra decoration
        }
    }

    /** Draw a dog face */
    public static void drawDog(Graphics2D g, int x, int y, int size, Color fur, boolean isBoss) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int cx = x + size / 2;
        int cy = y + size / 2;
        int r = size / 2 - 4;

        // ears on both sides
        g.setColor(fur.darker());
        g.fillOval(cx - r - 6, cy - r + 6, 16, 30);
        g.fillOval(cx + r - 10, cy - r + 6, 16, 30);

        // Round head
        g.setColor(fur);
        g.fillOval(cx - r, cy - r, r * 2, r * 2);

        // Protruding snout nose
        g.fillOval(cx - 14, cy + 2, 28, 18);
        g.setColor(Color.BLACK);
        g.fillOval(cx - 5, cy + 6, 10, 8);

        // Eyes
        g.setColor(Color.WHITE);
        g.fillOval(cx - r / 2 - 6, cy - 8, 12, 12);
        g.fillOval(cx + r / 2 - 6, cy - 8, 12, 12);
        g.setColor(Color.BLACK);
        g.fillOval(cx - r / 2 - 2, cy - 5, 6, 6);
        g.fillOval(cx + r / 2 - 2, cy - 5, 6, 6);

        if (isBoss) {
            g.setColor(Color.WHITE);
            g.fillPolygon(new int[]{cx - 6, cx - 2, cx - 10}, new int[]{cy + 18, cy + 18, cy + 28}, 3);
            g.fillPolygon(new int[]{cx + 6, cx + 2, cx + 10}, new int[]{cy + 18, cy + 18, cy + 28}, 3);
            g.setStroke(new BasicStroke(2f));
            g.drawLine(cx - r / 2 - 8, cy - 14, cx - r / 2 + 2, cy - 10);
            g.drawLine(cx + r / 2 + 8, cy - 14, cx + r / 2 - 2, cy - 10);
        }
    }
}
