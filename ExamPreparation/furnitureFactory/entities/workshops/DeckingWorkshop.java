package furnitureFactory.entities.workshops;

public class DeckingWorkshop extends BaseWorkshop{

private int producedFurnitureCount;

    private static final int DECKING_WOOD_QUANTITY_REDUCE_FACTOR = 150;

    public DeckingWorkshop(int woodQuantity) {
        super(woodQuantity, DECKING_WOOD_QUANTITY_REDUCE_FACTOR);
    }

    @Override
    public int getProducedFurnitureCount() {
        return producedFurnitureCount;
    }

    @Override
    public void produce() {
        if (this.getWoodQuantity() >= DECKING_WOOD_QUANTITY_REDUCE_FACTOR) {
            producedFurnitureCount++;
            this.setWoodQuantity(this.getWoodQuantity() - DECKING_WOOD_QUANTITY_REDUCE_FACTOR);
        }
    }
}
