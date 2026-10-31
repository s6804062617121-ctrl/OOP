package cardbattle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

/**
 * LanePanel = renders one lane on the board as a vertical column
 */
public class LanePanel extends JPanel {

    private Lane lane;
    private final int laneIndex;
    private final GamePanel parent;

    public LanePanel(int laneIndex, GamePanel parent) {
        this.laneIndex = laneIndex;
        this.parent = parent;
        setPreferredSize(new Dimension(140, 420));
        setOpaque(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                parent.onLaneClicked(laneIndex);
            }
        });
    }

    public void setLane(Lane lane) {
        this.lane = lane;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        int mid = h / 2;

        // white column background
        g2.setColor(new Color(255, 255, 255, 150));
        g2.fillRoundRect(2, 2, w - 4, h - 4, 16, 16);
        g2.setColor(new Color(90, 90, 90, 180));
        g2.drawRoundRect(2, 2, w - 4, h - 4, 16, 16);

        // boss side / player side
        g2.setColor(Color.DARK_GRAY);
        g2.drawLine(8, mid, w - 8, mid);
        g2.setFont(new Font("Tahoma", Font.BOLD, 11));
        g2.setColor(new Color(255, 255, 255, 210));
        g2.fillRoundRect(w / 2 - 14, mid - 17, 28, 14, 8, 8);
        g2.setColor(new Color(60, 60, 60));
        g2.drawString("VS", w / 2 - 9, mid - 6);

        if (lane != null) {
            Minion m = lane.getMinion();
            PlayerCard pc = lane.getPlayerCard();

            // boss minion
            if (m != null) {
                m.drawArt(g2, w / 2 - 28, 14, 56);
                drawStatsRow(g2, w, 74, m.getHp(), m.getAtk());
                g2.setColor(Color.BLACK);
                g2.setFont(new Font("Tahoma", Font.PLAIN, 10));
                g2.drawString(trim(m.getName()), 8, mid - 8);
            } else {
                g2.setColor(Color.GRAY);
                g2.setFont(new Font("Tahoma", Font.ITALIC, 10));
                g2.drawString("(empty)", w / 2 - 16, mid / 2);
            }

            //  player card
            if (pc != null) {
                pc.drawArt(g2, w / 2 - 28, mid + 14, 56);
                drawStatsRow(g2, w, mid + 74, pc.getHp(), pc.getAtk());
                g2.setColor(Color.BLACK);
                g2.setFont(new Font("Tahoma", Font.PLAIN, 10));
                g2.drawString(trim(pc.getName()), 8, h - 8);
            } else {
                g2.setColor(Color.GRAY);
                g2.setFont(new Font("Tahoma", Font.ITALIC, 10));
                g2.drawString("Click to place a card", w / 2 - 38, mid + mid / 2);
            }
        }

        g2.dispose();
    }

    /** Draw a row with heart+HP and sword+ATK  */
    private void drawStatsRow(Graphics2D g2, int w, int y, int hp, int atk) {
        IconArtist.drawHeart(g2, w / 2 - 26, y, 14, new Color(210, 40, 40));
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Tahoma", Font.BOLD, 10));
        g2.drawString(String.valueOf(hp), w / 2 - 10, y + 11);

        IconArtist.drawSword(g2, w / 2 + 4, y, 15, new Color(90, 90, 90));
        g2.drawString(String.valueOf(atk), w / 2 + 20, y + 11);
    }

    private String trim(String s) {
        return s.length() > 9 ? s.substring(0, 8) + "…" : s;
    }
}