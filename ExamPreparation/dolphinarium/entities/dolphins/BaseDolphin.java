package dolphinarium.entities.dolphins;

import dolphinarium.entities.foods.Food;

import static dolphinarium.common.ExceptionMessages.DOLPHIN_NAME_NULL_OR_EMPTY;

public abstract class BaseDolphin implements Dolphin{

    private String name;
    private int energy;

    public BaseDolphin(String name, int energy) {
        this.setName(name);
        this.setEnergy(energy);
    }

    public void setName(String name) {
        if (name == null || name.isBlank()){
            throw new NullPointerException (DOLPHIN_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    public void setEnergy(int energy) {
        if(energy <= 0){
            energy = 0;
        }
        this.energy = energy;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getEnergy() {
        return energy;
    }

    @Override
    public void eat(Food food) {
        if(food.getClass().getSimpleName().equals("Squid")){
            energy += 175;
        } else if (food.getClass().getSimpleName().equals("Herring")){
            energy += 200;
        } else if (food.getClass().getSimpleName().equals("Mackerel")){
            energy += 305;
        }
    }
}
