package cardbattle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JButton;

/**
 * CardButton = a button that shows one card from the player hand 
 */
public class CardButton extends JButton {

    private final PlayerCard card;
    private boolean selected = false;

    public CardButton(PlayerCard card) {
        this.card = card;
        setPreferredSize(new Dimension(92, 132));
        setFocusPainted(false);
        setContentAreaFilled(false);
    }

    public PlayerCard getCard() { return card; }

    public void setSelected(boolean selected) {
        this.selected = selected;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();

        // Card background 
        g2.setColor(selected ? new Color(255, 255, 180) : Color.WHITE);
        g2.fillRoundRect(2, 2, w - 4, h - 4, 12, 12);
        g2.setColor(selected ? Color.ORANGE : Color.DARK_GRAY);
        g2.setStroke(new BasicStroke(selected ? 3f : 1f));
        g2.drawRoundRect(2, 2, w - 4, h - 4, 12, 12);

        // Character art 
        card.drawArt(g2, w / 2 - 25, 6, 50);

        // Card name
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Tahoma", Font.BOLD, 10));
        g2.drawString(trim(card.getName()), 6, 68);

        // Heart icon + HP (left)
        IconArtist.drawHeart(g2, 4, 76, 16, new Color(210, 40, 40));
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Tahoma", Font.BOLD, 11));
        g2.drawString(String.valueOf(card.getHp()), 22, 88);

        // Sword icon + ATK (right)
        IconArtist.drawSword(g2, w - 24, 76, 18, new Color(90, 90, 90));
        g2.drawString(String.valueOf(card.getAtk()), w - 46, 88);

        // Mana icon + Cost (bottom center)
        IconArtist.drawMana(g2, w / 2 - 9, 100, 18, new Color(40, 120, 220));
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Tahoma", Font.BOLD, 10));
        g2.drawString(String.valueOf(card.getCost()), w / 2 - 3, 112);

        g2.dispose();
    }

    private String trim(String s) {
        return s.length() > 9 ? s.substring(0, 8) + "…" : s;
    }
}
