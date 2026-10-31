package cardbattle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/** Minion = boss minion  */
public class Minion extends Card {

    private String imageFile; 

    public Minion(String name, int hp, int atk, Color furColor, String imageFile) {
        super(name, hp, atk, 0, furColor);
        this.imageFile = imageFile;
    }

    @Override
    public void drawArt(Graphics2D g, int x, int y, int size) {
        BufferedImage img = imageFile != null ? ImageLoader.load(imageFile) : null;
        if (img != null) {
            g.drawImage(img, x, y, size, size, null);
        } else {
            CardArtist.drawDog(g, x, y, size, furColor, false);
        }
    }
}
