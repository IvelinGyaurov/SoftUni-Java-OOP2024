package competition.entities.destination;

import competition.entities.car.Car;

import java.util.ArrayList;
import java.util.Collection;

import static competition.common.ExceptionMessages.DESTINATION_NAME_NULL_OR_EMPTY;
import static competition.common.ExceptionMessages.NEGATIVE_DISTANCE_VALUE;

public abstract class BaseDestination implements Destination {

    private String name;
    private int distance;

    private Collection<Car> cars;

    public BaseDestination(String name, int distance) {
        this.setName(name);
        this.setDistance(distance);
        this.cars = new ArrayList<>();
    }

    private void setName(String name) {
        if (name == null || name.isEmpty() || name.isBlank()){
            throw new IllegalArgumentException(DESTINATION_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    private void setDistance(int distance) {
        if (distance <= 0){
            throw new IllegalArgumentException(NEGATIVE_DISTANCE_VALUE);
        }
        this.distance = distance;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Collection<Car> getCars() {
        return cars;
    }

    @Override
    public int getDistance() {
        return distance;
    }
}
