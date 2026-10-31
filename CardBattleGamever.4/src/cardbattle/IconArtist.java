package cardbattle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * IconArtist draws stat icons (heart = HP, sword = ATK, mana crystal = Cost)
 */
public class IconArtist {

    /** Heart icon for HP */
    public static void drawHeart(Graphics2D g, int x, int y, int size, Color color) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int lobeR = size / 3;
        g.setColor(color);
        g.fillOval(x, y, lobeR * 2, lobeR * 2);
        g.fillOval(x + size - lobeR * 2, y, lobeR * 2, lobeR * 2);
        g.fillPolygon(
                new int[]{x, x + size / 2, x + size},
                new int[]{y + lobeR, y + size, y + lobeR}, 3);
    }

    /**
     * Sword icon for ATK
     */
    public static void drawSword(Graphics2D g, int x, int y, int size, Color color) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.translate(x + size / 2, y + size / 2);
        g2.rotate(Math.toRadians(45));
 
        int bladeW = (int) (size * 0.26);
        int bladeTopY = -size / 2;
        int bladeBottomY = (int) (size * 0.08);
        int tipY = bladeTopY - (int) (size * 0.14); 
 
        Color steel = color.darker();
 
        // Blade body 
        int[] bx = {0, bladeW / 2, bladeW / 2, -bladeW / 2, -bladeW / 2};
        int[] by = {tipY, bladeTopY, bladeBottomY, bladeBottomY, bladeTopY};
        g2.setColor(steel);
        g2.fillPolygon(bx, by, 5);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1.3f));
        g2.drawPolygon(bx, by, 5);
        // Centerline groove down 
        g2.setColor(steel.brighter());
        g2.drawLine(0, tipY + 2, 0, bladeBottomY - 2);
 
        // Crossguard 
        g2.setColor(Color.BLACK);
        g2.fillRoundRect(-bladeW, bladeBottomY, bladeW * 2, (int) (size * 0.1), 3, 3);
 
        // Grip with a wrapped-leather look 
        int gripW = (int) (size * 0.16);
        int gripY = bladeBottomY + (int) (size * 0.1);
        int gripH = (int) (size * 0.22);
        g2.setColor(new Color(120, 80, 40));
        g2.fillRect(-gripW / 2, gripY, gripW, gripH);
        g2.setColor(new Color(80, 50, 20));
        for (int i = 1; i < 3; i++) {
            int ly = gripY + (gripH * i) / 3;
            g2.drawLine(-gripW / 2, ly, gripW / 2, ly);
        }
        g2.setColor(Color.BLACK);
        g2.drawRect(-gripW / 2, gripY, gripW, gripH);
 
        // Round pommel at the very end of the handle
        int pommelR = (int) (size * 0.1);
        g2.setColor(Color.DARK_GRAY);
        g2.fillOval(-pommelR / 2, gripY + gripH, pommelR, pommelR);
        g2.setColor(Color.BLACK);
        g2.drawOval(-pommelR / 2, gripY + gripH, pommelR, pommelR);
 
        g2.dispose();
    }
    /** Mana crystal icon  for Cost */
    public static void drawMana(Graphics2D g, int x, int y, int size, Color color) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int cx = x + size / 2;
        g.setColor(color);
        g.fillPolygon(
                new int[]{cx, x + size, cx, x},
                new int[]{y, y + size / 2, y + size, y + size / 2}, 4);
        g.setColor(color.brighter());
        g.fillPolygon(
                new int[]{cx, x + size - 4, cx},
                new int[]{y + 3, y + size / 2, y + size / 2 - 3}, 3);
        g.setStroke(new BasicStroke(1f));
        g.setColor(color.darker());
        g.drawPolygon(
                new int[]{cx, x + size, cx, x},
                new int[]{y, y + size / 2, y + size, y + size / 2}, 4);
    }
}
