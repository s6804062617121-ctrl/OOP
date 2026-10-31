package cardbattle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Boss = The Dog Overlord

 */
public class Boss {

    private final String name = "The Dog Overlord";
    private final int maxHp = 72; // tuned so a reasonably played game wins slightly more than half the time
    private int hp = maxHp;

    private static final Random RAND = new Random();

    // Name, color and image file for each minion type (matched by index)
    private static final String[] MINION_NAMES = {
        "Angry Puppy", "Guard Dog", "Howling Wolf", "Chasing Hound"
    };
    private static final Color[] MINION_COLORS = {
        new Color(200, 160, 120),
        new Color(120, 90, 60),
        new Color(90, 90, 90),
        new Color(150, 110, 70)
    };
    private static final String[] MINION_IMAGES = {
        "dog_puppy.png", "dog_guard.png", "dog_wolf.png", "dog_hound.png"
    };

    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public boolean isAlive() { return hp > 0; }

    public void takeDamage(int dmg) {
        hp -= dmg;
        if (hp < 0) {
            hp = 0;
        }
    }

    /**
     * Summon one new minion
     */
    public Minion summonMinion(int turnNumber) {
        int idx = RAND.nextInt(MINION_NAMES.length);
        int baseHp = 2 + (int) (turnNumber * 1.0);
        int baseAtk = 1 + (int) (turnNumber * 0.5);
        int hp = Math.max(1, baseHp + RAND.nextInt(6) - 1);   // variance -1 to +4
        int atk = Math.max(1, baseAtk + RAND.nextInt(4) - 1); // variance -1 to +2
        return new Minion(MINION_NAMES[idx], hp, atk, MINION_COLORS[idx], MINION_IMAGES[idx]);
    }

    public void drawArt(Graphics2D g, int x, int y, int size) {
        BufferedImage img = ImageLoader.load("dog_boss.png");
        if (img != null) {
            g.drawImage(img, x, y, size, size, null);
        } else {
            CardArtist.drawDog(g, x, y, size, new Color(90, 70, 60), true);
        }
    }
}
