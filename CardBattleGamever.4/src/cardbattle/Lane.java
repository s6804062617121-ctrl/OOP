package cardbattle;

/**
 * Lane = one column on the board 
 */
public class Lane {

    private PlayerCard playerCard; 
    private Minion minion;         

    public PlayerCard getPlayerCard() { return playerCard; }
    public Minion getMinion() { return minion; }

    public boolean isPlayerSlotEmpty() { return playerCard == null; }
    public boolean isMinionSlotEmpty() { return minion == null; }

    public void placePlayerCard(PlayerCard card) { this.playerCard = card; }
    public void placeMinion(Minion m) { this.minion = m; }

    /**
     * Resolve combat in this lane once (called at the end of the player's turn).
     */
    public String resolveCombat() {
        StringBuilder log = new StringBuilder();

        if (playerCard != null && playerCard.isAlive() && minion != null && minion.isAlive()) {
            int pAtk = playerCard.getAtk();
            int mAtk = minion.getAtk();

            playerCard.takeDamage(mAtk);
            minion.takeDamage(pAtk);

            log.append(playerCard.getName()).append(" clashes with ").append(minion.getName())
               .append(" (-").append(mAtk).append("/-").append(pAtk).append(" HP)\n");

            if (!playerCard.isAlive()) {
                log.append(playerCard.getName()).append(" died!\n");
                playerCard = null;
            }
            if (!minion.isAlive()) {
                log.append(minion.getName()).append(" died!\n");
                minion = null;
            }
        }
        return log.toString();
    }

    public boolean canAttackBossDirectly() {
        return playerCard != null && playerCard.isAlive() && minion == null;
    }

    public int getDirectDamage() {
        return canAttackBossDirectly() ? playerCard.getAtk() : 0;
    }
}
