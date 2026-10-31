package cardbattle;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** HomePanel =  art background +Start button */
public class HomePanel extends JPanel {

    public HomePanel(CardBattleGame mainFrame) {
        setLayout(new GridBagLayout()); 

        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setOpaque(false); 

        JLabel title = new JLabel("Cat vs Dog");
        title.setFont(new Font("Tahoma", Font.BOLD, 28));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("A 4-Lane Card Battle Game");
        subtitle.setFont(new Font("Tahoma", Font.PLAIN, 14));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startBtn = new JButton("Start");
        startBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
        startBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        startBtn.addActionListener(e -> mainFrame.startNewGame());

        box.add(title);
        box.add(Box.createVerticalStrut(8));
        box.add(subtitle);
        box.add(Box.createVerticalStrut(30));
        box.add(startBtn);

        add(box);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BackgroundArtist.drawCover((Graphics2D) g, getWidth(), getHeight(), "home");
    }
}
