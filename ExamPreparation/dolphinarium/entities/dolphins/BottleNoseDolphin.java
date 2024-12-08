package dolphinarium.entities.dolphins;

import dolphinarium.entities.foods.Food;

public class BottleNoseDolphin extends BaseDolphin{


    public BottleNoseDolphin(String name, int energy) {
        super(name, energy);
    }

    @Override
    public void jump() {
        int currentEnergy = getEnergy() - 200;
        setEnergy(Math.max(currentEnergy,0));
    }

}
