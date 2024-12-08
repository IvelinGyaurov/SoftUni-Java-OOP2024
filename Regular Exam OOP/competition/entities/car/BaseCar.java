package competition.entities.car;

import static competition.common.ExceptionMessages.CAR_MODEL_NULL_OR_EMPTY;

public abstract class BaseCar implements Car {

    private String model;
    private int batteryCapacity;

    private int mileage;

    public BaseCar(String model, int batteryCapacity) {
        this.setModel(model);
        this.setBatteryCapacity(batteryCapacity);
        this.mileage = 0;
    }

    private void setModel(String model) {
        if (model == null || model.isBlank() || model.isEmpty()){
            throw new NullPointerException (CAR_MODEL_NULL_OR_EMPTY);
        }
        this.model = model;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        if (batteryCapacity < 0){
            batteryCapacity = 0;
        }
        this.batteryCapacity = batteryCapacity;
    }

    @Override
    public void drive() {
        int newBattCap = this.getBatteryCapacity() - 15;
        this.mileage = mileage + 25;
        setBatteryCapacity(newBattCap);
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    @Override
    public int getMileage() {
        return mileage;
    }
}
