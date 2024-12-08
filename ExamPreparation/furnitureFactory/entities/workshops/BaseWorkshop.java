package furnitureFactory.entities.workshops;

import furnitureFactory.entities.wood.Wood;
import furnitureFactory.repositories.WoodRepository;

public abstract class BaseWorkshop implements Workshop{

    private int woodQuantity;
    private int producedFurnitureCount;
    private int woodQuantityReduceFactor;

    public BaseWorkshop(int woodQuantity, int woodQuantityReduceFactor) {
        this.setWoodQuantity(woodQuantity);
        this.producedFurnitureCount = 0;
        this.woodQuantityReduceFactor = woodQuantityReduceFactor;
    }

    public void setWoodQuantity(int woodQuantity) {
        if (woodQuantity <= 0){
            woodQuantity = 0;
        }
        this.woodQuantity = woodQuantity;
    }

    @Override
    public int getWoodQuantity() {
        return woodQuantity;
    }


    @Override
    public int getWoodQuantityReduceFactor() {
        return woodQuantityReduceFactor;
    }

    @Override
    public void addWood(Wood wood) {
        this.woodQuantity += wood.getWoodQuantity();
    }

}
