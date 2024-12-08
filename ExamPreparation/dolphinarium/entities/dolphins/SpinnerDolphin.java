package dolphinarium.entities.dolphins;

public class SpinnerDolphin extends BaseDolphin{
    public SpinnerDolphin(String name, int energy) {
        super(name, energy);
    }

    @Override
    public void jump() {
        int currentEnergy = getEnergy() - 50;
        setEnergy(Math.max(currentEnergy,0));
    }
}
