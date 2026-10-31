package cardbattle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 * GamePanel = the main gameplay screen
 */
public class GamePanel extends JPanel {

    private final CardBattleGame mainFrame;
    private GameManager gm;

    private LanePanel[] lanePanels;
    private JPanel handPanel;
    private JLabel turnLabel;
    private JLabel manaLabel;
    private JPanel manaIconPanel;
    private JLabel bossHpLabel;
    private JProgressBar bossHpBar;
    private JTextArea logArea;
    private JPanel bossArtPanel;

    private int selectedHandIndex = -1;

    public GamePanel(CardBattleGame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(8, 8));
        setBackground(new Color(235, 245, 235));

        add(buildTopPanel(), BorderLayout.NORTH);
        add(buildBattlefield(), BorderLayout.CENTER);
        add(buildBottomPanel(), BorderLayout.SOUTH);
    }

    // turn / mana / boss
    private JPanel buildTopPanel() {
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        JPanel info = new JPanel(new GridLayout(1, 2));
        info.setOpaque(false);
        turnLabel = new JLabel("Turn 1 / " + GameManager.MAX_TURNS, SwingConstants.CENTER);
        turnLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        info.add(turnLabel);

        // Mana bar
        manaIconPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                IconArtist.drawMana((Graphics2D) g, 2, 2, 20, new Color(40, 120, 220));
            }
        };
        manaIconPanel.setOpaque(false);
        manaIconPanel.setPreferredSize(new Dimension(24, 24));
        manaLabel = new JLabel("1/1", SwingConstants.LEFT);
        manaLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        JPanel manaBox = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 0));
        manaBox.setOpaque(false);
        manaBox.add(manaIconPanel);
        manaBox.add(manaLabel);
        info.add(manaBox);

        top.add(info, BorderLayout.NORTH);

        JPanel bossPanel = new JPanel(new BorderLayout(8, 0));
        bossPanel.setOpaque(false);

        bossArtPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (gm != null) {
                    gm.getBoss().drawArt((Graphics2D) g, 5, 5, 70);
                }
            }
        };
        bossArtPanel.setPreferredSize(new Dimension(80, 80));
        bossArtPanel.setOpaque(false);

        bossHpLabel = new JLabel("Boss", SwingConstants.LEFT);
        bossHpLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        bossHpBar = new JProgressBar(0, 100);
        bossHpBar.setForeground(new Color(200, 40, 40));
        bossHpBar.setStringPainted(true);

        JPanel bossText = new JPanel(new GridLayout(2, 1));
        bossText.setOpaque(false);
        bossText.add(bossHpLabel);
        bossText.add(bossHpBar);

        bossPanel.add(bossArtPanel, BorderLayout.WEST);
        bossPanel.add(bossText, BorderLayout.CENTER);
        top.add(bossPanel, BorderLayout.SOUTH);

        return top;
    }

    // 4 vertical lanes 
    private JScrollPane buildBattlefield() {
        JPanel battlefield = new JPanel(new GridLayout(1, GameManager.LANE_COUNT, 8, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                BackgroundArtist.drawGrassField((Graphics2D) g, getWidth(), getHeight());
            }
        };
        battlefield.setOpaque(false);
        battlefield.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

        lanePanels = new LanePanel[GameManager.LANE_COUNT];
        for (int i = 0; i < GameManager.LANE_COUNT; i++) {
            lanePanels[i] = new LanePanel(i, this);
            battlefield.add(lanePanels[i]);
        }

        JScrollPane scroll = new JScrollPane(battlefield);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    // hand + log + end-turn 
    private JPanel buildBottomPanel() {
        JPanel bottom = new JPanel(new BorderLayout(4, 4));
        bottom.setOpaque(false);

        handPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 4));
        handPanel.setOpaque(false);
        JScrollPane handScroll = new JScrollPane(handPanel);
        handScroll.setPreferredSize(new Dimension(100, 150));
        handScroll.setBorder(BorderFactory.createTitledBorder("Your Hand (click to select, then click a lane to place)"));

        logArea = new JTextArea(5, 20);
        logArea.setEditable(false);
        logArea.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createTitledBorder("Battle Log"));

        JButton endTurnBtn = new JButton("End Turn");
        endTurnBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        endTurnBtn.addActionListener(e -> onEndTurn());

        JPanel south = new JPanel(new BorderLayout());
        south.setOpaque(false);
        south.add(logScroll, BorderLayout.CENTER);
        south.add(endTurnBtn, BorderLayout.EAST);

        bottom.add(handScroll, BorderLayout.CENTER);
        bottom.add(south, BorderLayout.SOUTH);
        return bottom;
    }

    public void newGame() {
        gm = new GameManager();
        selectedHandIndex = -1;
        refreshAll();
        logArea.setText("Game start! Place your cards into the lanes to fight " + gm.getBoss().getName() + "\n");
    }

    public void onLaneClicked(int laneIndex) {
        if (selectedHandIndex == -1) {
            return;
        }
        boolean ok = gm.playCard(selectedHandIndex, laneIndex);
        if (ok) {
            selectedHandIndex = -1;
            refreshAll();
        } else {      
            logArea.append("Can't place that card here (not enough mana, or this lane is already occupied)\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        }
    }

    private void onCardClicked(int handIndex) {
        selectedHandIndex = (selectedHandIndex == handIndex) ? -1 : handIndex;
        refreshHand();
    }

    private void onEndTurn() {
        String log = gm.endTurn();
        logArea.append("\n--- End of turn ---\n" + log);
        logArea.setCaretPosition(logArea.getDocument().getLength());
        refreshAll();

        if (gm.getState() == GameManager.State.WIN) {
            mainFrame.showResult(true, gm.getTurn());
        } else if (gm.getState() == GameManager.State.LOSE) {
            mainFrame.showResult(false, gm.getTurn());
        }
    }

    private void refreshAll() {
        turnLabel.setText("Turn " + gm.getTurn() + " / " + GameManager.MAX_TURNS);
        manaLabel.setText(gm.getCurrentMana() + "/" + gm.getMaxMana());
        bossHpLabel.setText(gm.getBoss().getName() + "  (" + gm.getBoss().getHp() + "/" + gm.getBoss().getMaxHp() + ")");
        bossHpBar.setMaximum(gm.getBoss().getMaxHp());
        bossHpBar.setValue(gm.getBoss().getHp());
        bossArtPanel.repaint();

        for (int i = 0; i < lanePanels.length; i++) {
            lanePanels[i].setLane(gm.getLanes()[i]);
            lanePanels[i].repaint();
        }
        refreshHand();
    }

    private void refreshHand() {
        handPanel.removeAll();
        List<PlayerCard> hand = gm.getHand();
        for (int i = 0; i < hand.size(); i++) {
            CardButton btn = new CardButton(hand.get(i));
            btn.setSelected(i == selectedHandIndex);
            final int idx = i;
            btn.addActionListener(e -> onCardClicked(idx));
            handPanel.add(btn);
        }
        handPanel.revalidate();
        handPanel.repaint();
    }
}