package cardbattle;

import java.util.ArrayList;
import java.util.List;

/**
 * GameManager controlling the whole flow
 */
public class GameManager {

    public enum State { PLAYING, WIN, LOSE }

    public static final int MAX_TURNS = 12;
    public static final int MAX_HAND = 8;
    public static final int LANE_COUNT = 4;

    private Deck deck;
    private Boss boss;
    private Lane[] lanes;
    private List<PlayerCard> hand;
    private int turn;
    private int currentMana;
    private int maxMana;
    private State state;

    public GameManager() {
        newGame();
    }

    public void newGame() {
        deck = new Deck();
        boss = new Boss();
        lanes = new Lane[LANE_COUNT];
        for (int i = 0; i < LANE_COUNT; i++) {
            lanes[i] = new Lane();
        }
        hand = new ArrayList<>();
        turn = 1;
        maxMana = 1;
        currentMana = maxMana;
        state = State.PLAYING;

        for (int i = 0; i < 4; i++) {
            drawCard();
        }
    }

    private void drawCard() {
        if (hand.size() >= MAX_HAND) {
            return;
        }
        PlayerCard c = deck.draw();
        if (c != null) {
            hand.add(c);
        }
    }

    public boolean playCard(int handIndex, int laneIndex) {
        if (state != State.PLAYING) return false;
        if (handIndex < 0 || handIndex >= hand.size()) return false;
        if (laneIndex < 0 || laneIndex >= LANE_COUNT) return false;

        PlayerCard card = hand.get(handIndex);
        if (card.getCost() > currentMana) return false;
        if (!lanes[laneIndex].isPlayerSlotEmpty()) return false;

        currentMana -= card.getCost();
        lanes[laneIndex].placePlayerCard(card);
        hand.remove(handIndex);
        return true;
    }

    public String endTurn() {
        StringBuilder log = new StringBuilder();

        for (Lane lane : lanes) {
            log.append(lane.resolveCombat());
        }

        for (Lane lane : lanes) {
            if (lane.canAttackBossDirectly()) {
                int dmg = lane.getDirectDamage();
                boss.takeDamage(dmg);
                log.append(lane.getPlayerCard().getName())
                   .append(" attacks the boss directly! -").append(dmg).append(" HP\n");
            }
        }

        if (!boss.isAlive()) {
            state = State.WIN;
            return log.toString();
        }

        for (Lane lane : lanes) {
            if (lane.isMinionSlotEmpty()) {
                Minion m = boss.summonMinion(turn);
                lane.placeMinion(m);
                log.append("Boss summons ").append(m.getName())
                   .append(" (HP ").append(m.getHp()).append(" ATK ").append(m.getAtk()).append(")\n");
            }
        }

        turn++;
        if (turn > MAX_TURNS) {
            state = State.LOSE;
            return log.toString();
        }
        maxMana = turn;
        currentMana = maxMana;
        drawCard();

        return log.toString();
    }

    public Lane[] getLanes() { return lanes; }
    public List<PlayerCard> getHand() { return hand; }
    public Boss getBoss() { return boss; }
    public int getTurn() { return turn; }
    public int getCurrentMana() { return currentMana; }
    public int getMaxMana() { return maxMana; }
    public State getState() { return state; }
    public int getDeckRemaining() { return deck.remaining(); }
}
