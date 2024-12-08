package forgottenBattleships.entities.battleship;

import static forgottenBattleships.common.ExceptionMessages.SHIP_NAME_NULL_OR_EMPTY;

public abstract class BaseBattleship implements Battleship {

    private String name;
    private int health;
    private int ammunition;
    private int hitStrength;

    public BaseBattleship(String name, int health, int ammunition, int hitStrength) {
        this.setName(name);
        this.setHealth(health);
        this.setAmmunition(ammunition);
        this.hitStrength = hitStrength;
    }

    private void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException(SHIP_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    private void setHealth(int health) {
        if (health <= 0) {
            health = 0;
        }
        this.health = health;
    }

    private void setAmmunition(int ammunition) {
        if (ammunition <= 0) {
            ammunition = 0;
        }
        this.ammunition = ammunition;
    }

    @Override
    public void attack(Battleship battleship) {
       this.setAmmunition(this.ammunition - hitStrength);
    }


    @Override
    public void takeDamage(Battleship battleship) {
        this.setHealth(this.health - battleship.getHitStrength());
    }

    @Override
    public String getName() {
        return name;
    }

    private void setHitStrength(int hitStrength) {
        this.hitStrength = hitStrength;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public int getAmmunition() {
        return ammunition;
    }

    @Override
    public int getHitStrength() {
        return hitStrength;
    }
}
