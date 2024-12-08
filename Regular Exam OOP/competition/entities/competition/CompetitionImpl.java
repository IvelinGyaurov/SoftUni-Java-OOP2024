package competition.entities.competition;

import competition.entities.car.Car;
import competition.entities.destination.Destination;

import java.util.Collection;

public class CompetitionImpl implements Competition{

    @Override
    public void startVoyage(Destination destination, Collection<Car> cars) {
        int neededDistance = destination.getDistance();
        for (Car car : cars){
            while (car.getBatteryCapacity() >= 15 && car.getMileage() < neededDistance){
                car.drive();
                if (car.getBatteryCapacity() <= 0 || car.getMileage() >= neededDistance){
                    break;
                }
            }
        }
    }
}
