package cardbattle;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Card is the abstract parent class 
 */
public abstract class Card {

    protected String name;
    protected int maxHp;
    protected int hp;
    protected int atk;
    protected int cost;      
    protected Color furColor; 

    public Card(String name, int maxHp, int atk, int cost, Color furColor) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.atk = atk;
        this.cost = cost;
        this.furColor = furColor;
    }

    /** Reduce HP when attacked */
    public void takeDamage(int dmg) {
        hp -= dmg;
        if (hp < 0) {
            hp = 0;
        }
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getAtk() { return atk; }
    public int getCost() { return cost; }
    public Color getFurColor() { return furColor; }

   
    public abstract void drawArt(Graphics2D g, int x, int y, int size);
}
