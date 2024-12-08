package dolphinarium.entities.dolphins;

public class SpottedDolphin extends BaseDolphin{
    public SpottedDolphin(String name, int energy) {
        super(name, energy);
    }

    @Override
    public void jump() {
        int currentEnergy = getEnergy() - 100;
        setEnergy(Math.max(currentEnergy,0));
    }
}
