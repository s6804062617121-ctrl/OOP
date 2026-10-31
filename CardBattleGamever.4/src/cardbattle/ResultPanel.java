package cardbattle;

import java.awt.Color;
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

/** ResultPanel = end-game screen */
public class ResultPanel extends JPanel {

    private final JLabel resultLabel;
    private final JLabel detailLabel;
    private boolean win = true; 

    public ResultPanel(CardBattleGame mainFrame) {
        setLayout(new GridBagLayout());

        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setOpaque(false);

        resultLabel = new JLabel("Battle Result");
        resultLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        detailLabel = new JLabel(" ");
        detailLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        detailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton homeBtn = new JButton("Home");
        homeBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
        homeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        homeBtn.addActionListener(e -> mainFrame.showHome());

        box.add(resultLabel);
        box.add(Box.createVerticalStrut(12));
        box.add(detailLabel);
        box.add(Box.createVerticalStrut(30));
        box.add(homeBtn);

        add(box);
    }

    /** Called by CardBattleGame when the game ends */
     public void setResult(boolean win, int turnsUsed) {
        this.win = win;
        resultLabel.setForeground(Color.WHITE);
        detailLabel.setForeground(Color.WHITE);
        if (win) {
            resultLabel.setText("You Win! The Dog Overlord is defeated");
            detailLabel.setText("Took " + turnsUsed + " turns");
        } else {
            resultLabel.setText("You Lose... the boss survived");
            detailLabel.setText("All " + GameManager.MAX_TURNS + " turns used. Try again!");
       }
        repaint(); 
    //redraw so the cover art matches the latest result
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BackgroundArtist.drawCover((Graphics2D) g, getWidth(), getHeight(), win ? "win" : "lose");
    }
}
