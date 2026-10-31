package cardbattle;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class CardBattleGame extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel container;
    private final GamePanel gamePanel;
    private final ResultPanel resultPanel;

    public CardBattleGame() {
        setTitle("Cat vs Dog");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(760, 760);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        HomePanel homePanel = new HomePanel(this);
        gamePanel = new GamePanel(this);
        resultPanel = new ResultPanel(this);

        container.add(homePanel, "home");
        container.add(gamePanel, "game");
        container.add(resultPanel, "result");

        add(container);
        cardLayout.show(container, "home");
    }

    public void startNewGame() {
        gamePanel.newGame();
        cardLayout.show(container, "game");
    }

    public void showResult(boolean win, int turnsUsed) {
        resultPanel.setResult(win, turnsUsed);
        cardLayout.show(container, "result");
    }

    public void showHome() {
        cardLayout.show(container, "home");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CardBattleGame().setVisible(true));
    }
}
