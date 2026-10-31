package cardbattle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Deck = the player 20 card 
 */
public class Deck {

    private List<PlayerCard> cards = new ArrayList<>();
    private int drawIndex = 0;
    private static final Random RAND = new Random();

    private static final String[] TIER_NAMES = {
        "Scout Cat", "Fighter Cat", "Ninja Cat", "Knight Cat", "Golden Lion King"
    };
    private static final Color[] TIER_COLORS = {
        new Color(180, 180, 180),
        new Color(230, 140, 60),
        new Color(50, 50, 50),
        new Color(245, 245, 245),
        new Color(255, 200, 60)
    };
    // each row = {hpMin, hpMax, atkMin, atkMax, cost}
    private static final int[][] TIER_STATS = {
        {3, 4, 1, 2, 1},
        {5, 6, 2, 3, 2},
        {7, 9, 3, 5, 3},
        {10, 12, 5, 7, 4},
        {13, 16, 7, 10, 5}
    };

    public Deck() {
        for (int tier = 0; tier < 5; tier++) {
            int[] s = TIER_STATS[tier];
            for (int i = 0; i < 4; i++) {
                int hp = s[0] + RAND.nextInt(s[1] - s[0] + 1);
                int atk = s[2] + RAND.nextInt(s[3] - s[2] + 1);
                int cost = s[4];
                cards.add(new PlayerCard(TIER_NAMES[tier], hp, atk, cost, TIER_COLORS[tier], tier + 1));
            }
        }
        Collections.shuffle(cards);
    }

    public boolean hasNext() {
        return drawIndex < cards.size();
    }

    public PlayerCard draw() {
        if (!hasNext()) {
            return null;
        }
        return cards.get(drawIndex++);
    }

    public int remaining() {
        return cards.size() - drawIndex;
    }
}
