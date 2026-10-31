package cardbattle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/** PlayerCard = player side card  */
public class PlayerCard extends Card {

    private int tier; 

    public PlayerCard(String name, int maxHp, int atk, int cost, Color furColor, int tier) {
        super(name, maxHp, atk, cost, furColor);
        this.tier = tier;
    }

    public int getTier() {
        return tier;
    }

    @Override
    public void drawArt(Graphics2D g, int x, int y, int size) {
        //images/cat_tier1.png ... cat_tier5.png
        BufferedImage img = ImageLoader.load("cat_tier" + tier + ".png");
        if (img != null) {
            g.drawImage(img, x, y, size, size, null);
        } else {
            //  fall back to drawing with code
            CardArtist.drawCat(g, x, y, size, furColor, tier);
        }
    }
}
